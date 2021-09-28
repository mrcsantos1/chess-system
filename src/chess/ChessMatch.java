package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Integer turn;
    private Color currentPlayer;

    private final Board board;
    private boolean check;
    private boolean checkMate;

    private final List<Piece> piecesOnTheBoard = new ArrayList<>();
    private final List<Piece> capturedPieces = new ArrayList<>();


    public ChessMatch() {
        this.board = new Board(8, 8);
        this.turn = 1;
        this.currentPlayer = Color.WHITE;

        this.initialSetup();
    }

    public Board getBoard() {
        return this.board;
    }

    public boolean getCheck() {
        return this.check;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[this.getBoard().getRows()][this.getBoard().getColumns()];

        for (int i = 0; i < this.getBoard().getRows(); i++) {
            for (int j = 0; j < this.getBoard().getColumns(); j++) {
                mat[i][j] = (ChessPiece) this.getBoard().piece(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        this.validateSourcePosition(position);
        return this.board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        this.validateSourcePosition(source);
        this.validateTargetPosition(source, target);

        Piece capturedPiece = makeMove(source, target);

        if (this.testCheck(this.currentPlayer)) {
            this.undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        check = this.testCheck(this.opponent(currentPlayer));

        if (this.testCheckMate(this.opponent(this.currentPlayer))) {
            this.checkMate = true;
        } else {
            this.nextTurn();
        }

        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) this.board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = this.board.removePiece(target);

        this.board.placePiece(p, target);

        if (capturedPiece != null) {
            this.piecesOnTheBoard.remove(capturedPiece);
            this.capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!this.board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position. ");
        }

        if (this.currentPlayer != ((ChessPiece) this.board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours. ");
        }

        if (!this.board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chose piece. ");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!this.board.piece(source).possibleMove(target)) {
            throw new ChessException("The cosen piece can't move to target position. ");
        }
    }

    public void nextTurn() {
        this.turn++;
        this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }


    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) this.board.removePiece(target);
        p.decreaseMoveCount();

        this.board.placePiece(p, source);

        if (capturedPiece != null) {
            this.board.placePiece(capturedPiece, target);
            this.capturedPieces.remove(capturedPiece);
            this.piecesOnTheBoard.add(capturedPiece);
        }
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }


    private boolean testCheckMate(Color color) {
        if (!this.testCheck(color)) {
            return false;
        }
        List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());

        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < this.board.getRows(); i++) {
                for (int j = 0; j < this.board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);

                        boolean testCheck = this.testCheck(color);
                        this.undoMove(source, target, capturedPiece);

                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }


    private void placeNewPiece(Character column, Integer row, ChessPiece piece) {
        this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
        this.piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        this.placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        this.placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        this.placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        this.placeNewPiece('e', 1, new King(board, Color.WHITE));
        this.placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        this.placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        this.placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        this.placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        this.placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        this.placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        this.placeNewPiece('e', 8, new King(board, Color.BLACK));
        this.placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        this.placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        this.placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }

    public Integer getTurn() {
        return this.turn;
    }

    public Color getCurrentPlayer() {
        return this.currentPlayer;
    }


    public boolean isCheckMate() {
        return this.checkMate;
    }


}

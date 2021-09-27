package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    private final Board board;

    public ChessMatch() {
        this.board = new Board(8, 8);

        this.initialSetup();
    }

    public Board getBoard() {
        return board;
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

    private void placeNewPiece(Character column, Integer row, ChessPiece piece) {
        this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        this.placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        this.placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        this.placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        this.placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        this.placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        this.placeNewPiece('d', 1, new King(board, Color.WHITE));

        this.placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        this.placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        this.placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        this.placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}

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
        this.placeNewPiece('b', 6, new Rook(this.board, Color.WHITE));
        this.placeNewPiece('c', 4, new Rook(this.board, Color.BLACK));
        this.placeNewPiece('e', 8, new King(this.board, Color.BLACK));
        this.placeNewPiece('a', 2, new King(this.board, Color.WHITE));
    }
}

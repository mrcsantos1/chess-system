package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

        Position p = new Position(0, 0);

        if (this.getColor() == Color.WHITE) {
            //
            p.setValues(position.getRow() - 1, position.getColumn());
            if (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (
                    this.getBoard().positionExists(p) &&
                            !this.getBoard().thereIsAPiece(p) &&
                            this.getBoard().positionExists(p2) &&
                            !this.getBoard().thereIsAPiece(p2) &&
                            this.getMoveCount() == 0
            ) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        } else {
            //
            p.setValues(position.getRow() + 1, position.getColumn());
            if (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (
                    this.getBoard().positionExists(p) &&
                            !this.getBoard().thereIsAPiece(p) &&
                            this.getBoard().positionExists(p2) &&
                            !this.getBoard().thereIsAPiece(p2) &&
                            this.getMoveCount() == 0
            ) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            //
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }


    @Override
    public String toString() {
        return "P";
    }
}

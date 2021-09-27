package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
    private final Color color;
    private Integer moveCount = 0;


    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void increaseMoveCount() {
        this.moveCount++;
    }

    public void decreaseMoveCount() {
        this.moveCount--;
    }

    public Integer getMoveCount() {
        return this.moveCount;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }


    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) this.getBoard().piece(position);
        return p != null && p.getColor() != this.color;
    }

}

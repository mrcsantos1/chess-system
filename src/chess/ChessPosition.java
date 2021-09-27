package chess;

import boardgame.Position;

public class ChessPosition {
    private final Character column;
    private final Integer row;

    public ChessPosition(Character column, Integer row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8. ");
        }
        this.column = column;
        this.row = row;
    }


    public Character getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    protected Position toPosition() {
        return new Position(8 - this.getRow(), this.getColumn() - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString() {
        return "" + this.getColumn() + this.getRow();
    }

}

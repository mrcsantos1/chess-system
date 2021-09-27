package boardgame;

public class Board {
    private final Integer rows;
    private final Integer columns;
    private final Piece[][] pieces;

    public Board(Integer rows, Integer columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: There must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[this.getRows()][this.getColumns()];
    }

    public Integer getRows() {
        return rows;
    }


    public Integer getColumns() {
        return columns;
    }


    public Piece piece(int row, int column) {

        if (this.positionExists(row, column)) {
            throw new BoardException("Position not on the board");
        }

        return this.pieces[row][column];
    }

    public Piece piece(Position position) {

        if (this.positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        return this.pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {

        if (this.thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }


        this.pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    private boolean positionExists(int row, int column) {
        return row < 0 || row >= this.getRows() || column < 0 || column >= this.getColumns();
    }

    public boolean positionExists(Position position) {
        return this.positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {

        if (this.positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        return this.piece(position) != null;
    }
}

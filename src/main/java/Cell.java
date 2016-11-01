public class Cell {
    public int xcoord;
    public int ycoord;

    /**
     * Constructs a Cell object given x and y coordinates.
     * @param  xcoord The x coordinate of the cell
     * @param  ycoord The y coordinate of the cell
     */
    public Cell(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * Translates a cell according to a given Direction. This cell is not modified.
     * @param  direction The Direction in which to translate the Cell
     * @return           The translated Cell.
     */
    public Cell moveInDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                return new Cell(xcoord - 1, ycoord);
            case RIGHT:
                return new Cell(xcoord + 1, ycoord);
            case UP:
                return new Cell(xcoord, ycoord - 1);
            case DOWN:
                return new Cell(xcoord, ycoord + 1);
            default:
                return new Cell(xcoord, ycoord);
        }
    }
}

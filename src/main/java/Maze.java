public class Maze {

    private int width = 0;
    private int height = 0;
    private byte[][] cells;

    /**
     * Constructs a Maze object given a width and height.
     * @param  width  The width of the maze
     * @param  height The height of the maze
     */
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new byte[width][height];
        for (int y = 0;y < height;y++) {
            for (int x = 0;x < width;x++) {
                cells[x][y] = -1;
            }
        }
    }

    /**
     * Determines whether or not the given cell has been visited.
     * @param  cell The cell to check
     * @return      A boolean value indicating whether or not the given cell has been visited
     */
    public boolean isCellVisited(Cell cell) {
        return cells[cell.xcoord][cell.ycoord] != -1;
    }

    /**
     * Adds a cell to the maze.
     * @param  cell      The Cell
     * @param  direction The direction of the cell
     */
    public void setCell(Cell cell, Direction direction) {
        switch (direction) {
            case NONE:
                cells[cell.xcoord][cell.ycoord] = 0;
                break;
            case LEFT:
                cells[cell.xcoord][cell.ycoord] = 1;
                break;
            case RIGHT:
                cells[cell.xcoord][cell.ycoord] = 2;
                break;
            case UP:
                cells[cell.xcoord][cell.ycoord] = 3;
                break;
            case DOWN:
                cells[cell.xcoord][cell.ycoord] = 4;
                break;
            default:
        }
    }

    /**
     * Gets the direction of a given cell in the maze.
     * @param  xcoord The x coordinate of the cell
     * @param  ycoord The y coordinate of the cell
     * @return        The Direction of the cell at the given coordinates
     */
    public Direction getCell(int xcoord, int ycoord) {
        switch (cells[xcoord][ycoord]) {
            case 0:
                return Direction.NONE;
            case 1:
                return Direction.LEFT;
            case 2:
                return Direction.RIGHT;
            case 3:
                return Direction.UP;
            case 4:
                return Direction.DOWN;
            default:
                return null;
        }
    }

    /**
     * Gets the height of the maze.
     * @return The height of the maze
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the maze.
     * @return The width of the maze.
     */
    public int getWidth() {
        return width;
    }
}

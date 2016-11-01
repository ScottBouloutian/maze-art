import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class MazeEngine {

    private Maze maze;
    private static final int CELL_SIZE = 1;
    private int beginHuntIndex = 0;
    private final BufferedImage image;

    /**
     * Constructs a MazeEngine object.
     * @param  image The image upon which the maze will be drawn
     */
    public MazeEngine(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Ensure image dimensions are odd
        if (width % 2 == 0) {
            width -= 1;
        }
        if (height % 2 == 0) {
            height -= 1;
        }
        this.image = image.getSubimage(0, 0, width, height);

        this.maze = new Maze((width - 1) / 2, (height - 1) / 2);
    }

    /**
     * Performs the computation to generate the maze. The hunt and kill algorithm is used to
     *   generate a maze fitted to the image.
     */
    public void generateMaze() {
        Neighbor hunt = new Neighbor(new Cell(0,0),Direction.NONE,new Cell(0,0));
        while (hunt != null) {
            kill(hunt.getParent(), reverseDirection(hunt.getDirection()));
            hunt = hunt();
        }
    }

    /**
     * Finds the opposite of a given direction.
     * @param  direction Any Direction
     * @return           The reverse of the given direction
     */
    private Direction reverseDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            default:
                return Direction.NONE;
        }
    }

    /**
     * Performs the hunt portion of the hunt and kill algorithm to find the first unvisited Cell
     *   which is adjacent to a visted one.
     * @return  The Neighbor which was found by the hunt
     */
    private Neighbor hunt() {
        int width = maze.getWidth();
        int height = maze.getHeight();

        for (int y = beginHuntIndex / width;y < height;y++) {
            for (int x = beginHuntIndex % width;x < width;x++) {
                if (!maze.isCellVisited(new Cell(x,y))) {
                    LinkedList<Neighbor> neighbors = getNeighbors(new Cell(x,y), true);
                    for (Neighbor neighbor:neighbors) {
                        if (maze.isCellVisited(neighbor.getChild())) {
                            beginHuntIndex++;
                            return neighbor;
                        }
                    }
                } else {
                    beginHuntIndex++;
                }
            }
        }

        return null;
    }

    /**
     * Performs the kill part of the hunt and kill algorithm. Ordinarily this would perform a
     *   random walk, however in this implementation there is directional bias based on the
     *   given input image. The walk continues until the current cell has no unvisited neighbors.
     * @param  start            The cell at which to begin the walk
     * @param  initialDirection The direction in which to begin the walk from the given cell
     */
    private void kill(Cell start, Direction initialDirection) {
        maze.setCell(start, initialDirection);
        Cell currentCell = start;
        while (currentCell != null) {
            Neighbor neighbor = chooseNeighbor(currentCell);
            currentCell = neighbor.getChild();
            if (currentCell != null) {
                maze.setCell(neighbor.getChild(), neighbor.getDirection());
            }
        }
    }

    /**
     * Chooses a neighbor of a given cell. The neighbor is chosen by selecting the neighbor with
     *   the greatest intensity difference in the given image.
     * @param  cell The cell whose neighbors are among those to be selected
     * @return      The Neighbor of the given cell which was selected
     */
    private Neighbor chooseNeighbor(Cell cell) {
        LinkedList<Neighbor> neighbors = getNeighbors(cell, false);

        if (neighbors.size() == 0) {
            return new Neighbor(cell,null,null);
        }

        Neighbor bestNeighbor = null;
        int maxIntensity = Integer.MIN_VALUE;

        for (Neighbor neighbor:neighbors) {
            Cell parent = neighbor.getParent();
            Cell child = neighbor.getChild();
            int intensity = image.getRGB(child.xcoord * 2 + 1, child.ycoord * 2 + 1)
                    - image.getRGB(parent.xcoord * 2 + 1, parent.ycoord * 2 + 1);

            if (intensity > maxIntensity) {
                maxIntensity = intensity;
                bestNeighbor = neighbor;
            }
        }

        return bestNeighbor;
    }

    /**
     * Gets either visited or unvisted neighbors of a given cell.
     * @param  cell    The cell whose neighbors are to be returned
     * @param  visited True indicates that visited neighbors should be returned, false indicates
     *                 unvisted neighbors
     * @return         A list of Neighbors
     */
    private LinkedList<Neighbor> getNeighbors(Cell cell, boolean visited) {
        LinkedList<Neighbor> neighbors = new LinkedList<Neighbor>();

        Cell leftCell = cell.moveInDirection(Direction.LEFT);
        if (leftCell.xcoord >= 0 && !(maze.isCellVisited(leftCell) ^ visited)) {
            neighbors.push(new Neighbor(cell,Direction.LEFT,leftCell));
        }

        Cell rightCell = cell.moveInDirection(Direction.RIGHT);
        if (rightCell.xcoord < maze.getWidth() && !(maze.isCellVisited(rightCell) ^ visited)) {
            neighbors.push(new Neighbor(cell,Direction.RIGHT,rightCell));
        }

        Cell upCell = cell.moveInDirection(Direction.UP);
        if (upCell.ycoord >= 0 && !(maze.isCellVisited(upCell) ^ visited)) {
            neighbors.push(new Neighbor(cell,Direction.UP,upCell));
        }

        Cell downCell = cell.moveInDirection(Direction.DOWN);
        if (downCell.ycoord < maze.getHeight() && !(maze.isCellVisited(downCell) ^ visited)) {
            neighbors.push(new Neighbor(cell,Direction.DOWN,downCell));
        }

        return neighbors;
    }

    /**
     * Renders the maze image to a given output file as a png image.
     * @param  file The file to which the maze image will be saved
     */
    public void renderMaze(File file) throws IOException {
        Graphics2D graphics = image.createGraphics();
        MazeArtist.drawMaze(maze, 1, graphics);
        ImageIO.write(image, "png", file);
    }
}

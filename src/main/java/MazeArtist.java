import java.awt.Color;
import java.awt.Graphics;

public class MazeArtist {

    /**
     * Draws a maze onto a given graphics object.
     * @param  maze     The maze which will be drawn
     * @param  cellSize The size in pixels of each cell of the maze
     * @param  graphics The graphics object used to draw the maze
     */
    public static void drawMaze(Maze maze, int cellSize, Graphics graphics) {
        int width = maze.getWidth();
        int height = maze.getHeight();

        graphics.setColor(Color.WHITE);

        // Draw the entrance to the maze
        graphics.fillRect(cellSize,0,cellSize,cellSize);

        // Draw the exit of the maze
        graphics.fillRect((width * 2 - 1) * cellSize,(height * 2) * cellSize, cellSize, cellSize);

        // Draw the actual maze
        for (int x = 0;x < width;x++) {
            for (int y = 0;y < height;y++) {
                Direction direction = maze.getCell(x,y);

                if (direction == null) {
                    return;
                }

                int xpos = x * 2 + 1;
                int ypos = y * 2 + 1;

                graphics.fillRect(xpos * cellSize, ypos * cellSize, cellSize, cellSize);
                switch (direction) {
                    case NONE:
                        break;
                    case LEFT:
                        graphics.fillRect((xpos + 1) * cellSize, ypos * cellSize, cellSize,
                                cellSize);
                        break;
                    case RIGHT:
                        graphics.fillRect((xpos - 1) * cellSize, ypos * cellSize, cellSize,
                                cellSize);
                        break;
                    case UP:
                        graphics.fillRect(xpos * cellSize, (ypos + 1) * cellSize, cellSize,
                                cellSize);
                        break;
                    case DOWN:
                        graphics.fillRect(xpos * cellSize, (ypos - 1) * cellSize, cellSize,
                                cellSize);
                        break;
                    default:
                }
            }
        }
    }
}

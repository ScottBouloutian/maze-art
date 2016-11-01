import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Driver {

    /**
     * The entry point into the program. Generates a maze which visually appears to be similar to a
     *   given input image. The resulting maze is then saved as a png image to a given output file.
     * @param  args The first two values must be the input and output file paths respectively
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the maze generator!");

        // Make sure the required arguments are provided
        if (args.length < 2 || args[0].isEmpty() || args[1].isEmpty()) {
            System.out.println("You must specify input and output filenames.");
            return;
        }

        // Read in the input image
        BufferedImage image;
        try {
            image = ImageIO.read(new File(args[0]));
        } catch (Exception e) {
            System.out.println("There was an error reading the input file.");
            return;
        }

        // Set up the maze engine
        MazeEngine engine = new MazeEngine(image);
        File output = new File(args[1]);

        // Generate and render the maze to the output file
        engine.generateMaze();
        try {
            engine.renderMaze(output);
        } catch (Exception e) {
            System.out.println("There was an error writing the maze to the output file.");
        }
    }
}

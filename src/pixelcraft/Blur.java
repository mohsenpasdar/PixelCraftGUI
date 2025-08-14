package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Blur converter that applies a simple blur effect
 * by averaging the color values of the surrounding pixels (3x3 block).
 */
public class Blur implements Converter {
    /**
     * Apply a simple blur to the input image.
     */
     @Override
     public Image convertImage(Image input) {
     int width = (int) input.getWidth();

     int height = (int) input.getHeight();

     WritableImage out = new WritableImage(width, height);
     PixelReader reader = input.getPixelReader();
     PixelWriter writer = out.getPixelWriter();

     for (int x = 0; x < width; x++) {
     for (int y = 0; y < height; y++) {
     writer.setArgb(x, y, averagePixelInt(reader, x, y, width, height));
     }
     }
     return out;
     }

    /**
     * Calculate the average ARGB value of a pixel and its surrounding 8 neighbors.
     */
    private int averagePixelInt(PixelReader reader, int x, int y, int width, int height) {
        int sumAlpha = 0;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;

        // Loop over the 3x3 neighborhood
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                int col = Math.min(Math.max(i, 0), width - 1);
                int row = Math.min(Math.max(j, 0), height - 1);

                ARGB pixel = new ARGB(reader.getArgb(col, row));

                sumAlpha += pixel.alpha;
                sumRed += pixel.red;
                sumGreen += pixel.green;
                sumBlue += pixel.blue;
            }
        }

        ARGB newPixel = new ARGB(sumAlpha / 9, sumRed / 9, sumGreen / 9, sumBlue / 9);
        return newPixel.toInt();
    }
}

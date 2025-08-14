package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Grayscale converter that transforms an image into shades of gray
 * by averaging the red, green, and blue color components of each pixel.
 */
public class Grayscale implements Converter {

    @Override
    public Image convertImage(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();

        WritableImage out = new WritableImage(width, height);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                ARGB px = new ARGB(reader.getArgb(x, y));
                int gray = (px.red + px.green + px.blue) / 3;
                ARGB after = new ARGB(px.alpha, gray, gray, gray);
                writer.setArgb(x, y, after.toInt());
            }
        }
        return out;
    }
}

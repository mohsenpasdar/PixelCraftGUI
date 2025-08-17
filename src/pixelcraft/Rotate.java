package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Rotate converter that rotates the image 90 degrees clockwise.
 */
public class Rotate implements Converter {

    @Override
    public Image convertImage(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();

        WritableImage out = new WritableImage(height, width); // swapped
        PixelReader r = input.getPixelReader();
        PixelWriter w = out.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = r.getArgb(x, y);
                // (x, y) -> (height - 1 - y, x)  // 90° clockwise
                w.setArgb(height - 1 - y, x, argb);
            }
        }
        return out;
    }
}

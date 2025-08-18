package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// Utility methods for reading, writing and copying images.
public class ImageIOService {
    // Read an image from disk.
    public static Image loadImage(String filePath) throws IOException {
        try (FileInputStream inputFile = new FileInputStream(new File(filePath))) {
            Image img = new Image(inputFile);
            if (img.isError()) {
                throw new IOException("Decode error: " + img.getException());
            }
            return deepCopyImage(img);
        }
    }

    // Write an image as PNG.
    public static void saveImage(Image image, String filePath) throws IOException {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
            }
        }
        ImageIO.write(bufferedImage, "png", new File(filePath));
    }

    // Create a detached copy of the image.
    public static Image deepCopyImage(Image src) {
        int w = (int) src.getWidth();
        int h = (int) src.getHeight();

        WritableImage output = new WritableImage(w, h);
        PixelReader pr = src.getPixelReader();
        PixelWriter pw = output.getPixelWriter();
        pw.setPixels(0, 0, w, h, pr, 0, 0);
        return output;
    }
}

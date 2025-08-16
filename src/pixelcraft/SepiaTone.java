package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class SepiaTone implements Converter {

    /**
     * Apply a sepia-tone filter to the input image.
     *
     * @param input the original Image
     * @return the processed Image with sepia-tone effect
     */
    @Override
    public Image convertImage(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();

        WritableImage output = new WritableImage(width, height);
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        // Loop through each pixel
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixelInt = reader.getArgb(i, j);
                ARGB pixelARGB = new ARGB(pixelInt);
                int alpha = pixelARGB.alpha;
                int red = pixelARGB.red;
                int green = pixelARGB.green;
                int blue = pixelARGB.blue;

                // Apply the Sepia tone transformation using standard coefficients
                // Sepia tone coefficients sourced from OpenCV's sepia filter implementation:
                // Reference: https://www.kaggle.com/code/ahedjneed/15-image-filters-with-deployment-opencv-streamlit
                int newRed = Math.min(255, (int) (0.393 * red + 0.769 * green + 0.189 * blue));
                int newGreen = Math.min(255, (int) (0.349 * red + 0.686 * green + 0.168 * blue));
                int newBlue = Math.min(255, (int) (0.272 * red + 0.534 * green + 0.131 * blue));

                // Create the new sepia-toned pixel
                ARGB newPixelARGB = new ARGB(alpha, newRed, newGreen, newBlue);
                int newPixelInt = newPixelARGB.toInt();

                // Set the new pixel value
                writer.setArgb(i, j, newPixelInt);
            }
        }
        return output;
    }
}

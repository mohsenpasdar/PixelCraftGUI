package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class FlipVertical implements Converter {
    /**
     * Flip the input image vertically.
     *
     * @param input the original Image
     * @return the flipped Image
     */
    @Override
    public Image convertImage(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();

        WritableImage output = new WritableImage(width, height);
        PixelWriter writer = output.getPixelWriter();

        // Loop over each column
        for (int i = 0; i < width; i++) {
            // Swap pixels only in the top half of the column
            for (int j = 0; j < height / 2 + 1; j++) {
                swapColumnPixels(i, j, input, writer);
            }
        }
        return output;
    }

    /**
     * Swap two pixels vertically across the center of the column.
     *
     * @param column current column index
     * @param row current row index
     * @param input the input image
     * @param writer the PixelWriter for output image
     */
    private void swapColumnPixels(int column, int row, Image input, PixelWriter writer) {
        PixelReader reader = input.getPixelReader();
        int height = (int) input.getHeight();
        int pixelIJ = reader.getArgb(column, row);
        int swappedPixel = reader.getArgb(column, height - row - 1);

        // Swap the pixel at (column, row) with the pixel at (column, height - row - 1)
        writer.setArgb(column, row, swappedPixel);
        writer.setArgb(column, height - row - 1, pixelIJ);
    }
}

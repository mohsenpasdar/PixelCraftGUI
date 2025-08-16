package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

public class FlipHorizontal implements Converter {

    /**
     * Flip the input image horizontally.
     *
     * @param input the original Image to be flipped
     * @return the horizontally flipped Image
     */
    @Override
    public Image convertImage(Image input) {
        int row = 0;
        WritableImage output = new WritableImage(
                (int) input.getWidth(),
                (int) input.getHeight()
        );
        PixelWriter writer = output.getPixelWriter();
        helperFlipAllRows(row, input, writer);
        return output;
    }

    /**
     * Recursively flip all rows in the image.
     *
     * @param row current row index being processed
     * @param image the Image to flip horizontally
     * @param writer the PixelWriter used to write pixels to output image
     */
    private void helperFlipAllRows(int row, Image image, PixelWriter writer) {
        if (row == image.getHeight()) {
            return; // Base case: all rows processed
        }
        int column = 0;
        helperFlipRow(column, row, image, writer);
        row++;
        helperFlipAllRows(row, image, writer);
    }

    /**
     * Recursively flip one row by swapping pixels horizontally.
     *
     * @param column current column index being processed
     * @param row current row index being processed
     * @param image the Image to flip horizontally
     * @param writer the PixelWriter used to write pixels to output image
     */
    private void helperFlipRow(int column, int row, Image image, PixelWriter writer) {
        int width = (int) image.getWidth();
        if (column >=  width / 2 + 1) {
            return; // Base case: finished half the row
        }
        swapRowPixels(column, row, image, writer);
        column++;
        helperFlipRow(column, row, image, writer);
    }

    /**
     * Swap two pixels horizontally across the center of the row.
     *
     * @param column current column index being processed
     * @param row current row index being processed
     * @param image the Image containing pixels to swap
     * @param writer the PixelWriter used to write swapped pixels
     */
    private void swapRowPixels(int column, int row, Image image, PixelWriter writer) {
        int width = (int) image.getWidth();
        PixelReader reader = image.getPixelReader();
        int pixelIJ = reader.getArgb(column, row);
        int swappedPixel = reader.getArgb(width - column - 1, row);
        writer.setArgb(column, row, swappedPixel);
        writer.setArgb(width - column - 1, row, pixelIJ);
    }
}

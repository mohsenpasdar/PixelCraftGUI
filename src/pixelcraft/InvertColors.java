package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


public class InvertColors implements Converter {

    /**
     * Invert the colors of the input image recursively.
     *
     * @param input the original Image
     * @return the Image with colors inverted
     */
    @Override
    public Image convertImage(Image input) {
        WritableImage output = new WritableImage((int) input.getWidth(), (int) input.getHeight());
        int row = 0;
        helperInvertAllRows(row, input, output);
        return output;
    }

    /**
     * Recursively invert all rows of the image.
     *
     * @param row current row index
     * @param input the Image to invert
     * @param output the WritableImage to store results
     */
    private void helperInvertAllRows(int row, Image input, WritableImage output) {
        if (row == input.getHeight()) {
            return; // Base case: all rows processed
        }
        int col = 0;
        helperInvertRow(col, row, input, output); // Start inverting this row
        helperInvertAllRows(row + 1, input, output); // Move to next row
    }

    /**
     * Recursively invert colors of pixels in a single row.
     *
     * @param col current column index
     * @param row current row index
     * @param input the Image to invert
     * @param output the WritableImage to store results
     */
    private void helperInvertRow(int col, int row, Image input, WritableImage output) {
        if (col == input.getWidth()) {
            return; // Base case: end of the row
        }
        invertPixel(col, row, input, output); // Invert current pixel
        helperInvertRow(col + 1, row, input, output); // Move to next column
    }

    /**
     * Invert color of a single pixel by subtracting each RGB component from 255.
     *
     * @param col column index of the pixel
     * @param row row index of the pixel
     * @param input the Image to read from
     * @param output the WritableImage to write to
     */
    private void invertPixel(int col, int row, Image input, WritableImage output) {
        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();
        ARGB pixelARGB = new ARGB(reader.getArgb(col, row));
        int alpha = pixelARGB.alpha;
        int red = 255 - pixelARGB.red; // Invert red channel
        int green = 255 - pixelARGB.green; // Invert green channel
        int blue = 255 - pixelARGB.blue; // Invert blue channel
        ARGB newPixelARGB = new ARGB(alpha, red, green, blue);
        writer.setArgb(col, row, newPixelARGB.toInt());
    }
}
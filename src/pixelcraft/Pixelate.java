package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Pixelate implements Converter {

    // Size of each pixelated block
    private final int blockSize;
    public static final int MIN_BLOCK = 3;

    public Pixelate(int blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * Apply a pixelation effect to the input image.
     *
     * @param input the original Image
     * @return a new Image with the pixelated effect
     */
    @Override
    public Image convertImage(Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelWriter writer = output.getPixelWriter();

        // Loop through image in blocks of blockSize
        for (int i = 0; i < width; i = i + blockSize) {
            for (int j = 0; j < height; j = j + blockSize) {
                int blockAvgColor = avgBlock(i, j, input);
                colorBlock(i, j, writer, blockAvgColor, width, height);
            }
        }

        return output;
    }

    /**
     * Calculate the average color of a blockSize x blockSize block.
     *
     * @param x starting x-coordinate of the block
     * @param y starting y-coordinate of the block
     * @param input the original Image
     * @return the averaged pixel color as an integer
     */
    private int avgBlock(int x, int y, Image input) {
        int width = (int) input.getWidth();
        int height = (int) input.getHeight();
        PixelReader reader = input.getPixelReader();

        int sumAlpha = 0;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;

        // Calculate the sum of colors within the block
        for (int i = x; i < x + blockSize; i++) {
            for (int j = y; j < y + blockSize; j++) {
                int col = Math.min(i, width - 1);
                int row = Math.min(j, height - 1);
                int pixelInt = reader.getArgb(col, row);
                ARGB pixelARGB = new ARGB(pixelInt);
                sumAlpha += pixelARGB.alpha;
                sumRed += pixelARGB.red;
                sumGreen += pixelARGB.green;
                sumBlue += pixelARGB.blue;
            }
        }

        int pixelsCount = blockSize * blockSize;

        // Return the average color of the block
        ARGB newPixelARGB = new ARGB(sumAlpha / pixelsCount, sumRed / pixelsCount, sumGreen / pixelsCount, sumBlue / pixelsCount);
        return newPixelARGB.toInt();
    }

    /**
     * Fill a blockSize x blockSize block in the new image with the specified color.
     *
     * @param x starting x-coordinate of the block
     * @param y starting y-coordinate of the block
     * @param writer the PixelWriter to set pixels
     * @param pixelInt the color to set for the block
     * @param width the width of the image
     * @param height the height of the image
     */
    private void colorBlock(int x, int y, PixelWriter writer, int pixelInt, int width, int height) {
        // Set each pixel in the block to the average color
        for (int i = x; i < x + blockSize; i++) {
            for (int j = y; j < y + blockSize; j++) {
                int col = Math.min(i, width - 1);
                int row = Math.min(j, height - 1);
                writer.setArgb(col, row, pixelInt);
            }
        }
    }
}

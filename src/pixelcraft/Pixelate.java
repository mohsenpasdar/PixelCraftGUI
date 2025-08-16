package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class Pixelate implements Converter {

    // Size of each pixelated block (can easily be changed)
    private static int BLOCK_SIZE = 3;
    private static final int MIN_BLOCK = 3;

    public static void resetCycle() { BLOCK_SIZE = MIN_BLOCK; }

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

        // Loop through image in blocks of BLOCK_SIZE
        for (int i = 0; i < width; i = i + BLOCK_SIZE) {
            for (int j = 0; j < height; j = j + BLOCK_SIZE) {
                int blockAvgColor = avgBlock(i, j, input);
                colorBlock(i, j, writer, blockAvgColor, width, height);
            }
        }

        int maxBlockAllowed = Math.max(MIN_BLOCK, Math.min(width, height) / 2);
        int next = BLOCK_SIZE + 2;                 // 3,5,7,9,...
        if (next > maxBlockAllowed) next = BLOCK_SIZE;
        BLOCK_SIZE = next;

        return output;
    }

    /**
     * Calculate the average color of a BLOCK_SIZE x BLOCK_SIZE block.
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
        for (int i = x; i < x + BLOCK_SIZE; i++) {
            for (int j = y; j < y + BLOCK_SIZE; j++) {
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

        int pixelsCount = BLOCK_SIZE * BLOCK_SIZE;

        // Return the average color of the block
        ARGB newPixelARGB = new ARGB(sumAlpha / pixelsCount, sumRed / pixelsCount, sumGreen / pixelsCount, sumBlue / pixelsCount);
        return newPixelARGB.toInt();
    }

    /**
     * Fill a BLOCK_SIZE x BLOCK_SIZE block in the new image with the specified color.
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
        for (int i = x; i < x + BLOCK_SIZE; i++) {
            for (int j = y; j < y + BLOCK_SIZE; j++) {
                int col = Math.min(i, width - 1);
                int row = Math.min(j, height - 1);
                writer.setArgb(col, row, pixelInt);
            }
        }
    }
}

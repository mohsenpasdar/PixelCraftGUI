package pixelcraft;

import javafx.scene.image.Image;

public class DiagonalMirror implements Converter {

    /**
     * Mirror the input image diagonally by first flipping it horizontally,
     * then flipping it vertically.
     *
     * @param input the original Image
     * @return the diagonally mirrored Image
     */
    @Override
    public Image convertImage(Image input) {
        // First, flip the image horizontally
        Converter horizontalConverter = new FlipHorizontal();
        Image flippedHorizontalImage = horizontalConverter.convertImage(input);

        // Then, flip the result vertically
        Converter verticalConverter = new FlipVertical();
        return verticalConverter.convertImage(flippedHorizontalImage);
    }
}

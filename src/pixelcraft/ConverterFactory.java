package pixelcraft;

public class ConverterFactory {
    private ConverterFactory() {};

    public static Converter createConverter(String converterId) {
        return switch (converterId.toLowerCase()) {
            case "grayscale" -> new Grayscale();
            case "rotate" -> new Rotate();
            case "blur" -> new Blur();
            case "flip_h" -> new FlipHorizontal();
            case "flip_v" -> new FlipVertical();
            case "sepia" -> new SepiaTone();
            case "pixelate" -> new Pixelate(Pixelate.MIN_BLOCK);
            case "invert" -> new InvertColors();
            case "diag_mirror" -> new DiagonalMirror();
            default -> throw new IllegalArgumentException("Unknown converter id: " + converterId);
        };
    }
}

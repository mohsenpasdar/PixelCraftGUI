package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PCModel {
    private final ArrayList<Observer> observers;
    private Image originalImage;
    private Image currentImage;
    private boolean dirty = false;

    public PCModel() {
        observers = new ArrayList<>();
    }

    public Image getImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void addObserver(PCView view) {
        observers.add(view);
        this.notifyObservers("observer added");
    }

    public void notifyObservers(String message) {
        for (Observer o: observers) {
            o.update(this, message);
        }
    }

    public void loadFromFile(String filePath) {
        try (FileInputStream inputFile = new FileInputStream(new File(filePath))) {
            Image img = new Image(inputFile);
            if (img.isError()) {
                System.err.println("Decode error: " + img.getException());
                notifyObservers("ERROR: decode");
                return;
            }
            this.currentImage = img;
            this.originalImage = img;
            this.dirty = false;
            notifyObservers("IMAGE_LOADED");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            notifyObservers("ERROR: io");
        }
    }

    public void resetToOriginal() {
        if (originalImage == null) return;
        this.currentImage = originalImage;
        this.dirty = false;
        notifyObservers("IMAGE_RESET");
    }

    public void apply(Converter converter) {
        if (currentImage == null) return;
        Image next = converter.convertImage(currentImage);
        if (next == null) {
            notifyObservers("ERROR: convert");
            return;
        };
        this.currentImage = next;
        this.dirty = true;
        notifyObservers("IMAGE_CHANGED");
    }

    public void saveCurrentImage(String filePath) {
        if (currentImage == null) return;
        try {
            saveImage(this.getImage(), filePath);
            notifyObservers("IMAGE_SAVED");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            notifyObservers("ERROR: io");
        }
    }

    public void saveImage(Image image, String filePath) throws IOException {

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Create a writable image
        WritableImage writableImage = new WritableImage(width, height);

        // Get the pixel reader from the original image
        PixelReader pixelReader = image.getPixelReader();

        // Create a BufferedImage
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Copy the pixels to the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
            }
        }

        // Save the BufferedImage as PNG
        try {
            ImageIO.write(bufferedImage, "png", new File(filePath));
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }
}

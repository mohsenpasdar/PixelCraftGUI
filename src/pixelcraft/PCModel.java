package pixelcraft;

import javafx.scene.image.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;

// Stores image state and manages history.
public class PCModel {
    private final ArrayList<Observer> observers;
    private Image originalImage;
    private Image currentImage;
    private boolean dirty = false;
    private final HistoryManager historyManager;
    private int pixelBlockSize = Pixelate.MIN_BLOCK;

    public PCModel() {
        observers = new ArrayList<>();
        historyManager = new HistoryManager();
    }

    public Image getImage() {
        return currentImage;
    }

    public boolean isDirty() {
        return dirty;
    }

    public Deque<HistoryEntry> getUndoStack() {
        return historyManager.getUndoStack();
    }

    public Deque<HistoryEntry> getRedoStack() {
        return historyManager.getRedoStack();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
        this.notifyObservers("observer added");
    }

    // Inform observers that something changed.
    public void notifyObservers(String message) {
        for (Observer o: observers) {
            o.update(this, message);
        }
    }

    // Load an image and reset model state.
    public void loadFromFile(String filePath) {
        try {
            Image img = ImageIOService.loadImage(filePath);
            this.originalImage = ImageIOService.deepCopyImage(img);
            this.currentImage = ImageIOService.deepCopyImage(img);
            this.dirty = false;
            this.clearHistory();
            this.pixelBlockSize = Pixelate.MIN_BLOCK;
            notifyObservers("IMAGE_LOADED");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            notifyObservers("ERROR: io");
        }
    }

    // Remove all undo/redo history.
    public void clearHistory() {
        historyManager.clearHistory();
    }

    // Revert to the originally loaded image.
    public void resetToOriginal() {
        if (originalImage == null) return;
        getRedoStack().clear();
        historyManager.recordState(ImageIOService.deepCopyImage(currentImage), dirty);
        this.currentImage = originalImage;
        this.dirty = false;
        this.pixelBlockSize = Pixelate.MIN_BLOCK;
        notifyObservers("IMAGE_RESET");
    }

    // Apply a converter and store the previous state.
    public void apply(Converter converter) {
        if (currentImage == null) return;
        Image next = converter.convertImage(currentImage);
        if (next == null) {
            notifyObservers("ERROR: convert");
            return;
        };
        getRedoStack().clear();
        historyManager.recordState(ImageIOService.deepCopyImage(currentImage), dirty);
        this.currentImage = next;
        this.dirty = true;
        if (converter instanceof Pixelate) {
            // Increase block size on repeated pixelation, within bounds.
            int width = (int) currentImage.getWidth();
            int height = (int) currentImage.getHeight();
            int maxBlockAllowed = Math.max(Pixelate.MIN_BLOCK, Math.min(width, height) / 2);
            int nextSize = pixelBlockSize + 2; // 3,5,7,9,...
            if (nextSize > maxBlockAllowed) nextSize = pixelBlockSize;
            pixelBlockSize = nextSize;
        }
        notifyObservers("IMAGE_CHANGED");
    }

    // Restore the previous image from history.
    public void undo() {
        if (historyManager.getUndoStack().isEmpty()) return;
        HistoryEntry e = historyManager.undo(ImageIOService.deepCopyImage(currentImage), dirty);
        this.currentImage = e.getImage();
        this.dirty = e.isDirty();
        notifyObservers("undo");
    }

    // Reapply the last undone change.
    public void redo() {
        if (getRedoStack().isEmpty()) return;
        HistoryEntry e = historyManager.redo(ImageIOService.deepCopyImage(currentImage), dirty);
        this.currentImage = e.getImage();
        this.dirty = e.isDirty();
        notifyObservers("redo");
    }

    // Write the current image to disk.
    public void saveCurrentImage(String filePath) {
        if (currentImage == null) return;
        try {
            ImageIOService.saveImage(this.getImage(), filePath);
            notifyObservers("IMAGE_SAVED");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            notifyObservers("ERROR: io");
        }
    }

    public int getPixelBlockSize() {
        return pixelBlockSize;
    }
}

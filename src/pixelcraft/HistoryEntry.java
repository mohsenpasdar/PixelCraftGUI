package pixelcraft;

import javafx.scene.image.Image;

// Snapshot of an image along with its dirty state.
public class HistoryEntry {
    private final Image image;
    private final boolean dirty;

    public HistoryEntry(Image image, boolean dirty) {
        this.image = image;
        this.dirty = dirty;
    }

    public Image getImage() {
        return image;
    }

    public boolean isDirty() {
        return dirty;
    }
}

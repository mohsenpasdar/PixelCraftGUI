package pixelcraft;

import javafx.scene.image.Image;

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

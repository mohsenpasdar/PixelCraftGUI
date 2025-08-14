package pixelcraft;

import javafx.scene.image.Image;

public interface Observer {
    void update(PCModel model, String message);
}

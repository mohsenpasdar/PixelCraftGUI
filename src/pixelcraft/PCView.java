package pixelcraft;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PCView implements Observer {
    BorderPane pane = new BorderPane();
    private final ImageView imageView = new ImageView();

    public PCView(Stage stage) {
        pane.setCenter(imageView);
        imageView.setPreserveRatio(true);
    }

    public void update(PCModel model, String message) {
        imageView.setImage(model.getImage());
    }
}

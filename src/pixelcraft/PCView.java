package pixelcraft;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PCView implements Observer {
    private final Stage stage;
    private final BorderPane pane;
    private final ImageView imageView;

    private final Button btnOpen = new Button("Open");
    private final Button btnSave = new Button("Save (PNG)");
    private final Button btnReset = new Button("Reset");
    private final Button btnGrayscale = new Button("Grayscale");
    private final Button btnRotate = new Button("Rotate");
    private final Button btnBlur = new Button("Blur");

    public PCView(Stage stage) {
        this.stage = stage;
        this.pane = new BorderPane();
        this.imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800); // optional sizing
        imageView.setFitHeight(600);

        buildUI();
    }

    private void buildUI() {

    }

    public Stage getStage() { return stage; }
    public BorderPane getPane() { return pane; }
    public ImageView getImageView() { return imageView; }

    public Button getBtnOpen() { return btnOpen; }
    public Button getBtnSave() { return btnSave; }
    public Button getBtnReset() { return btnReset; }
    public Button getBtnGrayscale() { return btnGrayscale; }
    public Button getBtnRotate() { return btnRotate; }
    public Button getBtnBlur() { return btnBlur; }

    public void update(PCModel model, String message) {
        imageView.setImage(model.getImage());
    }
}

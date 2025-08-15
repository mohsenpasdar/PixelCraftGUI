package pixelcraft;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PCView implements Observer {
    private final Stage stage;
    private final BorderPane pane;
    private final ImageView imageView;

    private final Button btnOpen = new Button("Open");
    private final Button btnSave = new Button("Save");
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

        setInitialDisabledState();

        Scene scene = new Scene(pane, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("PixelCraft GUI");
        stage.show();
    }

    private void buildUI() {
        VBox vBox = new VBox(10, btnOpen, btnSave, btnReset, new Separator(), btnGrayscale, btnRotate, btnBlur);
        vBox.setPadding(new Insets(10));
        vBox.setFillWidth(true);
        vBox.setStyle("-fx-background-color: lightblue;");

        btnOpen.setPrefWidth(120);
        btnSave.setPrefWidth(120);
        btnReset.setPrefWidth(120);
        btnGrayscale.setPrefWidth(120);
        btnRotate.setPrefWidth(120);
        btnBlur.setPrefWidth(120);

        btnOpen.setPadding(new Insets(8));
        btnSave.setPadding(new Insets(8));
        btnReset.setPadding(new Insets(8));
        btnGrayscale.setPadding(new Insets(8));
        btnRotate.setPadding(new Insets(8));
        btnBlur.setPadding(new Insets(8));

        pane.setLeft(vBox);
        pane.setCenter(imageView);
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

    private void setInitialDisabledState() {
        btnSave.setDisable(true);
        btnReset.setDisable(true);
        btnGrayscale.setDisable(true);
        btnRotate.setDisable(true);
        btnBlur.setDisable(true);
    }

    public void update(PCModel model, String message) {
        imageView.setImage(model.getImage());
        updateButtonStates(model);
    }

    private void updateButtonStates(PCModel model) {
        boolean hasImage = model.getImage() != null;
        boolean convertersEnabled = hasImage;
        boolean canReset = model.isDirty();
        boolean canSave = hasImage;

        btnGrayscale.setDisable(!convertersEnabled);
        btnRotate.setDisable(!convertersEnabled);
        btnBlur.setDisable(!convertersEnabled);

        btnReset.setDisable(!canReset);
        btnSave.setDisable(!canSave);
    }

}

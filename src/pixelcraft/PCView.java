package pixelcraft;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PCView implements Observer {
    private final Stage stage;
    private final BorderPane pane;
    private final ImageView imageView;
    private final Scene scene;

    private final Button btnOpen = new Button("Open");
    private final Button btnSave = new Button("Save");
    private final Button btnReset = new Button("Reset");
    private final Button btnUndo = new Button("Undo");
    private final Button btnRedo = new Button("Redo");
    private final Button btnGrayscale = new Button("Grayscale");
    private final Button btnRotate = new Button("Rotate");
    private final Button btnBlur = new Button("Blur");
    private final Button btnFlipH = new Button("Flip Horizontal");
    private final Button btnFlipV = new Button("Flip Vertical");
    private final Button btnMirror = new Button("Mirror Diagonal");
    private final Button btnPixelate = new Button("Pixelate");
    private final Button btnSepia = new Button("Sepia");
    private final Button btnInvert = new Button("Invert Colors");


    public PCView(Stage stage) {
        this.stage = stage;
        this.pane = new BorderPane();
        this.imageView = new ImageView();
        this.scene = new Scene(pane, 1000, 700);
        scene.getStylesheets().add(
                getClass().getResource("pixelcraft.css").toExternalForm()
        );

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800); // optional sizing
        imageView.setFitHeight(600);

        buildUI();
        setInitialDisabledState();
        stage.setScene(scene);
        setupAccelerators();
        stage.setTitle("PixelCraft GUI");
        stage.show();
    }

    private void buildUI() {
        VBox vBox = new VBox(10, btnOpen, btnSave, btnReset, new Separator(),
                btnGrayscale, btnRotate, btnBlur, btnFlipH, btnFlipV, btnMirror,
                btnPixelate, btnSepia, btnInvert);
        vBox.setPadding(new Insets(10));
        vBox.setFillWidth(true);
        vBox.getStyleClass().add("toolbox");

        HBox hBox = new HBox(5, btnUndo, btnRedo);
        hBox.setPadding(new Insets(10));
//        vBox.setFillWidth(true);
        hBox.getStyleClass().add("toolbox");

        pane.setLeft(vBox);
        pane.setTop(hBox);
        pane.setCenter(imageView);
    }
    

    public Stage getStage() { return stage; }
    public BorderPane getPane() { return pane; }
    public ImageView getImageView() { return imageView; }

    public Button getBtnOpen() { return btnOpen; }
    public Button getBtnSave() { return btnSave; }
    public Button getBtnReset() { return btnReset; }
    public Button getBtnUndo() { return btnUndo; }
    public Button getBtnRedo() { return btnRedo; }

    public Button getBtnGrayscale() { return btnGrayscale; }
    public Button getBtnRotate() { return btnRotate; }
    public Button getBtnBlur() { return btnBlur; }
    public Button getBtnFlipH() { return btnFlipH; }
    public Button getBtnFlipV() { return btnFlipV; }
    public Button getBtnMirror() { return btnMirror; }
    public Button getBtnPixelate() { return btnPixelate; }
    public Button getBtnSepia() { return btnSepia; }
    public Button getBtnInvert() { return btnInvert; }

    private void setInitialDisabledState() {
        btnSave.setDisable(true);
        btnReset.setDisable(true);
        btnUndo.setDisable(true);
        btnRedo.setDisable(true);
        btnGrayscale.setDisable(true);
        btnRotate.setDisable(true);
        btnBlur.setDisable(true);
        btnFlipH.setDisable(true);
        btnFlipV.setDisable(true);
        btnMirror.setDisable(true);
        btnPixelate.setDisable(true);
        btnSepia.setDisable(true);
        btnInvert.setDisable(true);
    }

    private void setupAccelerators() {
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN), () -> btnOpen.fire());
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN), () -> btnSave.fire());
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN), () -> btnReset.fire());
    }

    private void updateButtonStates(PCModel model) {
        boolean hasImage = model.getImage() != null;
        boolean convertersEnabled = hasImage;
        boolean canReset = model.isDirty();
        boolean canSave = hasImage;
        boolean canUndo = !model.getUndoStack().isEmpty();
        boolean canRedo = !model.getRedoStack().isEmpty();

        btnGrayscale.setDisable(!convertersEnabled);
        btnRotate.setDisable(!convertersEnabled);
        btnBlur.setDisable(!convertersEnabled);
        btnFlipH.setDisable(!convertersEnabled);
        btnFlipV.setDisable(!convertersEnabled);
        btnMirror.setDisable(!convertersEnabled);
        btnPixelate.setDisable(!convertersEnabled);
        btnSepia.setDisable(!convertersEnabled);
        btnInvert.setDisable(!convertersEnabled);

        btnReset.setDisable(!canReset);
        btnSave.setDisable(!canSave);
        btnUndo.setDisable(!canUndo);
        btnRedo.setDisable(!canRedo);
    }

    public void update(PCModel model, String message) {
        imageView.setImage(model.getImage());
        updateButtonStates(model);
    }
}

package pixelcraft;

import javafx.stage.FileChooser;

import java.io.File;

public class PCController {
    private final PCModel model;
    public PCView view;
    private File lastDir = null;

    public PCController(PCModel model, PCView view) {
        this.model = model;
        this.view = view;
    }

    public PCModel getModel() {
        return model;
    }

    public PCView getView() {
        return view;
    }

    public void onLoad() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png"));

        if (lastDir != null && lastDir.isDirectory()) {
            chooser.setInitialDirectory(lastDir);
        }

        File file = chooser.showOpenDialog(view.getStage());
        if (file == null) return; // user cancelled

        lastDir = file.getParentFile();
        model.loadFromFile(file.getAbsolutePath());
        Pixelate.resetCycle();
    }

    public void onApply(String selectedId) {
        Converter c = ConverterFactory.createConverter(selectedId);
        this.model.apply(c);
    }

    public void onSave() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Image As");

        chooser.setInitialFileName("untitled.png");

        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));

        File file = chooser.showSaveDialog(view.getStage());
        if (file != null) {
            // Ensure file name ends with .png
            String path = file.getAbsolutePath();
            if (!path.toLowerCase().endsWith(".png")) {
                path += ".png";
            }
            model.saveCurrentImage(path);
        }
    }

    public void onReset() {
        this.model.resetToOriginal();
        Pixelate.resetCycle();
    }



    public void installControllers() {
        this.getView().getBtnOpen().setOnAction(actionEvent -> onLoad());
        this.getView().getBtnSave().setOnAction(actionEvent -> onSave());
        this.getView().getBtnReset().setOnAction(actionEvent -> onReset());

        this.getView().getBtnSave().setDisable(model.getImage() == null);
        this.getView().getBtnGrayscale().setOnAction(actionEvent -> onApply("grayscale"));
        this.getView().getBtnRotate().setOnAction(actionEvent -> onApply("rotate"));
        this.getView().getBtnBlur().setOnAction(actionEvent -> onApply("blur"));
        this.getView().getBtnFlipH().setOnAction(actionEvent -> onApply("flip_h"));
        this.getView().getBtnFlipV().setOnAction(actionEvent -> onApply("flip_v"));
        this.getView().getBtnMirror().setOnAction(actionEvent -> onApply("diag_mirror"));
        this.getView().getBtnPixelate().setOnAction(actionEvent -> onApply("pixelate"));
        this.getView().getBtnSepia().setOnAction(actionEvent -> onApply("sepia"));
        this.getView().getBtnInvert().setOnAction(actionEvent -> onApply("invert"));

    }
}

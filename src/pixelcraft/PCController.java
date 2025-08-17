package pixelcraft;

import javafx.stage.FileChooser;

import java.io.File;

public class PCController {
    private final PCModel model;
    private final PCView view;
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
    }

    public void onApply(String selectedId) {
        Converter c;
        if ("pixelate".equals(selectedId)) {
            c = new Pixelate(model.getPixelBlockSize());
        } else {
            c = ConverterFactory.createConverter(selectedId);
        }
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
    }

    public void onUndo() {
        this.model.undo();
    }

    public void onRedo() {
        this.model.redo();
    }



    public void installControllers() {
        view.getBtnOpen().setOnAction(actionEvent -> onLoad());
        view.getBtnSave().setOnAction(actionEvent -> onSave());
        view.getBtnReset().setOnAction(actionEvent -> onReset());
        view.getBtnUndo().setOnAction(actionEvent -> onUndo());
        view.getBtnRedo().setOnAction(actionEvent -> onRedo());

        view.getBtnSave().setDisable(model.getImage() == null);
        view.getBtnGrayscale().setOnAction(actionEvent -> onApply("grayscale"));
        view.getBtnRotate().setOnAction(actionEvent -> onApply("rotate"));
        view.getBtnBlur().setOnAction(actionEvent -> onApply("blur"));
        view.getBtnFlipH().setOnAction(actionEvent -> onApply("flip_h"));
        view.getBtnFlipV().setOnAction(actionEvent -> onApply("flip_v"));
        view.getBtnMirror().setOnAction(actionEvent -> onApply("diag_mirror"));
        view.getBtnPixelate().setOnAction(actionEvent -> onApply("pixelate"));
        view.getBtnSepia().setOnAction(actionEvent -> onApply("sepia"));
        view.getBtnInvert().setOnAction(actionEvent -> onApply("invert"));

    }
}

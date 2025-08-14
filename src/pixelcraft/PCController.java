package pixelcraft;

public class PCController {
    private PCModel model;
    private PCView view;

    public PCController(PCModel model, PCView view) {
        this.model = model;
        this.view = view;
    }

    public void onLoad() {
        String path = "placeholder";
        if (path != null) {
            this.model.loadFromFile(path);
        }
    }

    public void onApply(String selectedId) {
        Converter c = ConverterFactory.createConverter(selectedId);
        this.model.apply(c);
    }

    public void onSave() {
        String path = "placeholder";
        if (path != null) {
            this.model.saveCurrentImage(path);
        }
    }

    public void onReset() {
        this.model.resetToOriginal();
    }

    public void installControllers() {
    }
}

package pixelcraft;

import javafx.application.Application;
import javafx.stage.Stage;

// Launches the editor and wires up the main components.
public class PixelCraftGUI extends Application {

    @Override
    public void start(Stage stage) {
        // Create MVC objects and show the window.
        PCModel model = new PCModel();
        Observer view = new PCView(stage);
        model.addObserver(view);

        PCController controller = new PCController(model, (PCView) view);
        controller.installControllers();
    }

    public static void main(String[] args) {
        // Standard JavaFX entry point.
        launch();
    }
}

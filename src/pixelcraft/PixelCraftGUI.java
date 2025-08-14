package pixelcraft;

import javafx.application.Application;
import javafx.stage.Stage;

public class PixelCraftGUI extends Application {

    @Override
    public void start(Stage stage) {

        PCModel model = new PCModel();
        PCView view = new PCView(stage);
        model.addObserver(view);

        PCController controller = new PCController(model, view);
        controller.installControllers();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
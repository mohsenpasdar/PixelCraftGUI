package pixelcraft;

// Simple observer used by the model to notify the view.
public interface Observer {
    void update(PCModel model, String message);
}

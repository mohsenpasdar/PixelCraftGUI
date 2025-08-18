package pixelcraft;

import javafx.scene.image.Image;
import java.util.ArrayDeque;
import java.util.Deque;

// Manages undo and redo stacks.
public class HistoryManager {
    private final Deque<HistoryEntry> undoStack = new ArrayDeque<>();
    private final Deque<HistoryEntry> redoStack = new ArrayDeque<>();
    private final int historyLimit = 20;

    public Deque<HistoryEntry> getUndoStack() {
        return undoStack;
    }

    public Deque<HistoryEntry> getRedoStack() {
        return redoStack;
    }

    // Remove all stored history.
    public void clearHistory() {
        redoStack.clear();
        undoStack.clear();
    }

    // Save a snapshot to the undo stack.
    public void recordState(Image image, boolean dirty) {
        pushBounded(undoStack, new HistoryEntry(image, dirty));
    }

    // Move one step back in history.
    public HistoryEntry undo(Image currentImage, boolean dirty) {
        redoStack.push(new HistoryEntry(currentImage, dirty));
        return undoStack.pop();
    }

    // Reapply the last undone state.
    public HistoryEntry redo(Image currentImage, boolean dirty) {
        undoStack.push(new HistoryEntry(currentImage, dirty));
        return redoStack.pop();
    }

    // Keep stacks within the size limit.
    private void pushBounded(Deque<HistoryEntry> stack, HistoryEntry entry) {
        if (stack.size() >= historyLimit) {
            stack.removeLast();
        }
        stack.push(entry);
    }
}

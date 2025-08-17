package pixelcraft;

import javafx.scene.image.Image;
import java.util.ArrayDeque;
import java.util.Deque;

public class HistoryManager {
    private final Deque<HistoryEntry> undoStack = new ArrayDeque<>();
    private final Deque<HistoryEntry> redoStack = new ArrayDeque<>();
    private final int historyLimit = 15;

    public Deque<HistoryEntry> getUndoStack() {
        return undoStack;
    }

    public Deque<HistoryEntry> getRedoStack() {
        return redoStack;
    }

    public void clearHistory() {
        redoStack.clear();
        undoStack.clear();
    }

    public void recordState(Image image, boolean dirty) {
        pushBounded(undoStack, new HistoryEntry(image, dirty));
    }

    public HistoryEntry undo(Image currentImage, boolean dirty) {
        redoStack.push(new HistoryEntry(currentImage, dirty));
        return undoStack.pop();
    }

    public HistoryEntry redo(Image currentImage, boolean dirty) {
        undoStack.push(new HistoryEntry(currentImage, dirty));
        return redoStack.pop();
    }

    private void pushBounded(Deque<HistoryEntry> stack, HistoryEntry entry) {
        if (stack.size() >= historyLimit) {
            stack.removeLast();
        }
        stack.push(entry);
    }
}

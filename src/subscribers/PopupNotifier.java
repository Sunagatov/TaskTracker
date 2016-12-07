package subscribers;

import tracker.api.IJournal;
import tracker.api.IJournalSubscriber;
import tracker.api.ITask;

import java.io.Serializable;

public class PopupNotifier implements IJournalSubscriber {
    @Override
    public void onEvent(IJournal sender, ITask task) {
        PopupNotifierWindow w = new PopupNotifierWindow(sender, task);
        w.setVisible(true);
    }
}

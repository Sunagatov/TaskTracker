package subscribers;

import tracker.api.IJournal;
import tracker.api.IJournalSubscriber;
import tracker.api.ITask;

public class BrowserLauncher implements IJournalSubscriber {
    @Override
    public void onEvent(IJournal sender, ITask task) {
        // https://tasktracker.org/?journal={$id}&task={$id}
    }
}

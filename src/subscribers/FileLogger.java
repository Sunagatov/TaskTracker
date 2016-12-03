package subscribers;

import tracker.api.IJournal;
import tracker.api.IJournalSubscriber;
import tracker.api.ITask;

public class FileLogger implements IJournalSubscriber {
    @Override
    public void onEvent(IJournal sender, ITask task) {
        System.out.println(String.format("journal=%d, taskId=%d, taskName=%s, taskDesc=%s",
                sender.getId(), task.getId(), task.getName(), task.getDescription()));
    }
}

import subscribers.PopupNotifier;
import tracker.api.IJournal;
import tracker.api.ITask;
import tracker.api.Period;
import tracker.api.PeriodOverlapException;
import tracker.impl.Manager;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {


    public static void main(String[] args) throws IOException, PeriodOverlapException {
        IJournal journal = Manager.getInstance().loadJournal(null);
        for (int i = 1; i < 5; ++i) {
            Period p = new Period(LocalDateTime.now().plusSeconds(5 * i), LocalDateTime.now().plusSeconds(5 * i + 4));
            addTask(journal, String.format("title%d", i), String.format("desc%d", i), p);
        }
        journal.register(new PopupNotifier());
    }

    private static void addTask(IJournal journal, String name, String description, Period period) throws PeriodOverlapException {
        ITask task = journal.createTask(period);
        task.setName(name);
        task.setDescription(description);
    }
}

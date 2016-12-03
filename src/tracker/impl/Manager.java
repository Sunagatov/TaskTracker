package tracker.impl;

import tracker.api.IJournal;
import tracker.api.IManager;

import java.io.IOException;

/**
 * Реализация интерфейса {@link IManager}.
 * Шаблон проектирования "Одиночка".
 */
public class Manager implements IManager {
    private static IManager intance = new Manager();

    /**
     * Возвращает единственный экземпляр {@link IManager}, осуществляющий управление журналами.
     *
     * @return Единственный экземпляр {@link IManager}, осуществляющий управление журналами.
     */
    public static IManager getInstance() {
        return intance;
    }

    private Manager() {
    }

    @Override
    public IJournal loadJournal(String name) throws IOException {
        // Временное решение (в далёком прекрасном будущем id будет браться из БД).
        return new Journal(0);
    }

    @Override
    public void saveJournal(IJournal journal, String name) throws IOException {
        throw new UnsupportedOperationException();
    }
}

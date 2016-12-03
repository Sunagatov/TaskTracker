package tracker.api;

import java.util.Collection;

/**
 * Исключение, генерируемое в случае наличия задач с пересекающимися периодами.
 */
public class PeriodOverlapException extends Exception {
    private Collection<ITask> overlappedTasks;

    /**
     * Создаёт исключение со списком задач, периоды которых пересекаются с новым периодом.
     *
     * @param overlappedTasks Список задач, периоды которых пересекаются с новым периодом.
     */
    public PeriodOverlapException(Collection<ITask> overlappedTasks) {
        this.overlappedTasks = overlappedTasks;
    }

    /**
     * Возвращает список задач, периоды которых пересекаются с новым периодом.
     *
     * @return Список задач, периоды которых пересекаются с новым периодом.
     */
    public Collection<ITask> getOverlappedTasks() {
        return overlappedTasks;
    }
}

package tracker.api;

/**
 * Базовый интерфейс для всех подписчиков на событие наступления задачи в журнале.
 */
public interface IJournalSubscriber {
    /**
     * Обрабатывает событие наступления задачи.
     *
     * @param sender Журнал, в котором произошло событие.
     * @param task   Задача, время наступления которой подошло.
     */
    void onEvent(IJournal sender, ITask task);
}

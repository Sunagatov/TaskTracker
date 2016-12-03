package tracker.api;

import java.util.Collection;

/**
 * Базовый интерфейс для всех журналов.
 */
public interface IJournal {
    /**
     * Возвращает уникальный идентификатор журнала.
     *
     * @return Уникальный идентификатор журнала.
     */
    int getId();

    /**
     * Возвращает коллекцию задач из указанного периода.
     *
     * @param period Период для поиска задач.
     * @return Коллекция задач из указанного периода.
     */
    Collection<ITask> getTasks(Period period);

    /**
     * Создаёт новую задачу с указанным периодом в коллекции задач.
     *
     * @param period Период задачи.
     * @return Созданная задача.
     * @throws PeriodOverlapException Если период новой задачи пересекается с любым из периодов уже существующих задач.
     */
    ITask createTask(Period period) throws PeriodOverlapException;

    /**
     * Удаляет задачу из коллекции.
     *
     * @param task Удаляемая задача.
     */
    void removeTask(ITask task);

    /**
     * Возвращает период указанной задачи.
     *
     * @param task Задача, период которой нужно получить.
     * @return Период указанной задачи.
     */
    Period getTaskPeriod(ITask task);

    /**
     * Устанавливает новый период для задачи.
     *
     * @param task   Задача, период которой нужно установить.
     * @param period Новое значение периода.
     * @throws PeriodOverlapException Если новый период задачи пересекается с любым из периодов других задач.
     */
    void setTaskPeriod(ITask task, Period period) throws PeriodOverlapException;

    /**
     * Регистрирует нового подписчика на событие наступления задачи.
     *
     * @param subscriber Новый подписчик.
     */
    void register(IJournalSubscriber subscriber);

    /**
     * Удаляет подписчика из события наступления задачи.
     *
     * @param subscriber Удаляемый подписчик.
     */
    void unregister(IJournalSubscriber subscriber);
}

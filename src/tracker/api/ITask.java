package tracker.api;

/**
 * Базовый интерфейс задачи.
 */
public interface ITask {
    /**
     * Возвращает идентификатор задачи (уникальный в пределах журнала).
     *
     * @return Идентификатор задачи (уникальный в пределах журнала).
     */
    int getId();

    /**
     * Возвращает название задачи.
     *
     * @return Название задачи.
     */
    String getName();

    /**
     * Возвращает описание задачи.
     *
     * @return Описание задачи.
     */
    String getDescription();

    /**
     * Устанавливает новое название задачи.
     *
     * @param name Новое название.
     */
    void setName(String name);

    /**
     * Устанавливает новое описание задачи.
     *
     * @param description Новое описание.
     */
    void setDescription(String description);
}

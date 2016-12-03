package tracker.impl;

import tracker.api.ITask;

/**
 * Реализация интерфейса {@link ITask}.
 */
class Task implements ITask {
    private final int id;
    private String name;
    private String description;

    /**
     * Создаёт задачу с указанным идентификатором.
     *
     * @param id Идентификатор задачи.
     */
    public Task(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}

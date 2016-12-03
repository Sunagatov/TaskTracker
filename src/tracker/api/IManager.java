package tracker.api;

import java.io.IOException;

/**
 * Базовый интерфейс для управления журналами.
 */
public interface IManager {
    /**
     * Загружает журнал по его имени.
     *
     * @param name Имя журнала.
     * @return Загруженный журнал.
     * @throws IOException Если возникла ошибка при загрузке журнала с указанным именем.
     */
    IJournal loadJournal(String name) throws IOException;

    /**
     * Сохраняет журнал, используя указанное имя.
     *
     * @param journal Сохраняемый журнал.
     * @param name    Имя сохраняемого журнала.
     * @throws IOException Если возникла ошибка при сохранении журнала с указанным именем.
     */
    void saveJournal(IJournal journal, String name) throws IOException;
}

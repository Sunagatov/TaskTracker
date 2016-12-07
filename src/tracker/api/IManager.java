package tracker.api;

import java.io.IOException;

/**
 * Базовый интерфейс для управления журналами.
 */
public interface IManager {
    /**
     * Загружает журнал по его имени.
     *
     * @param path Имя журнала.
     * @return Загруженный журнал.
     * @throws IOException Если возникла ошибка при загрузке журнала с указанным именем.
     */
    IJournal loadJournal(String path) throws IOException, ClassNotFoundException;

    /**
     * Сохраняет журнал, используя указанное имя.
     *
     * @param journal Сохраняемый журнал.
     * @param path    Имя сохраняемого журнала.
     * @throws IOException Если возникла ошибка при сохранении журнала с указанным именем.
     */
    void saveJournal(IJournal journal, String path) throws IOException;


    /**
     * Создает новый журнал.
     *
     * @return Новый журнал.
     */
    IJournal createJournal();
}

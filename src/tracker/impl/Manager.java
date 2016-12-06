package tracker.impl;

import tracker.api.IJournal;
import tracker.api.IManager;

import java.io.*;

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

    private Manager() { }

    @Override
    public IJournal loadJournal(String name) {
       /* FileInputStream fis = new FileInputStream(name+".out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        try {
            return  (IJournal) oin.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        // Временное решение (в далёком прекрасном будущем id будет браться из БД).
        return new Journal(0);
    }

    @Override
    public void saveJournal(IJournal journal, String name) throws IOException {
        /*FileOutputStream fos = new FileOutputStream("temp.out");
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(journal);
            oos.flush();
            oos.close();
        }*/
        throw new UnsupportedOperationException();
    }
}

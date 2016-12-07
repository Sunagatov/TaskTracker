package tracker.impl;

import tracker.api.IJournal;
import tracker.api.IManager;

import java.io.*;
import java.util.Random;

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
    public IJournal loadJournal(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream oin;
        try (FileInputStream fis = new FileInputStream(path)) {
            oin = new ObjectInputStream(fis);
            return (IJournal) oin.readObject();
        }
    }

    @Override
    public void saveJournal(IJournal journal, String path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("MyObject.txt"))) {
            out.writeObject(journal);
            out.close();
        }
        //throw new UnsupportedOperationException();
    }

    @Override
    public IJournal createJournal() {
        Random r = new Random();
        return new Journal(getRandomNumberInRange(0, Integer.MAX_VALUE-1));
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}

package tracker.api;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Описывает период времени.
 */
public class Period  {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Создаёт объект, описывающий фиксированный период времени.
     * @param start Начало периода.
     * @param end Конец периода.
     * @throws IllegalArgumentException Если конец периода предшествует началу.
     */
    public Period(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end))
            throw new IllegalArgumentException();
        this.start = start;
        this.end = end;
    }

    /**
     * Возвращает начало периода.
     * @return Начало периода.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Возвращает конец периода.
     * @return Конец периода.
     */
    public LocalDateTime getEnd() {
        return end;
    }
}

package tracker.impl;

import tracker.api.*;
import tracker.api.Period;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link IJournal}.
 */
class Journal implements IJournal {
    private class TimerTask extends java.util.TimerTask {
        private ITask task;

        public TimerTask(ITask task) {
            this.task = task;
        }

        public ITask getTask() {
            return task;
        }

        @Override
        public void run() {
            synchronized (subscribers) {
                for (IJournalSubscriber subscriber : subscribers) {
                    new Thread(() -> subscriber.onEvent(Journal.this, task)).run();
                }
            }
            Journal.this.reschedule();
        }
    }

    private final int id;
    private Map<ITask, Period> data = new HashMap<>();

    private List<IJournalSubscriber> subscribers = new ArrayList<>();

    private Timer timer = new Timer();
    private TimerTask timerTask;

    /**
     * Создаёт журнал с указанным идентификатором.
     *
     * @param id Идентификатор журнала.
     */
    public Journal(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public synchronized Collection<ITask> getTasks(Period period) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized ITask createTask(Period period) throws PeriodOverlapException {
        checkPeriod(null, period);
        ITask task = new Task(generateTaskId());
        data.put(task, period);
        rescheduleOnAdd(task);
        return task;
    }

    @Override
    public synchronized void removeTask(ITask task) {
        data.remove(task);
        rescheduleOnRemove(task);
    }

    @Override
    public synchronized Period getTaskPeriod(ITask task) {
        return data.get(task);
    }

    @Override
    public synchronized void setTaskPeriod(ITask task, Period period) throws PeriodOverlapException {
        checkPeriod(task, period);
        Period oldPeriod = data.get(task);
        data.put(task, period);
        rescheduleOnUpdate(task, oldPeriod);
    }

    @Override
    public synchronized void register(IJournalSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public synchronized void unregister(IJournalSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    private void checkPeriod(ITask task, Period newPeriod) throws PeriodOverlapException {
        LocalDateTime newStart = newPeriod.getStart();
        LocalDateTime newEnd = newPeriod.getEnd();
        List<ITask> overlaps = data.entrySet().stream().
                filter((entry) -> {
                    if (entry.getKey() == task)
                        return false;
                    Period period = entry.getValue();
                    LocalDateTime start = period.getStart();
                    LocalDateTime end = period.getEnd();
                    return start.isBefore(newStart) && newStart.isBefore(end) ||
                            start.isBefore(newEnd) && newEnd.isBefore(end) ||
                            newStart.isBefore(start) && end.isBefore(newEnd);
                }).
                map((entry) -> entry.getKey()).
                collect(Collectors.toList());
        if (overlaps.size() > 0)
            throw new PeriodOverlapException(overlaps);
    }

    private int generateTaskId() {
        while (data.entrySet().stream().anyMatch((entry) -> entry.getKey().getId() == taskId))
            ++taskId;
        return taskId;
    }

    private int taskId = 0;

    private void rescheduleOnAdd(ITask addedTask) {
        // Оптимизировать (вызывать reschedule только при действительной необходимости
        reschedule();
    }

    private void rescheduleOnRemove(ITask removedTask) {
        // Оптимизировать (вызывать reschedule только при действительной необходимости
        reschedule();
    }

    private void rescheduleOnUpdate(ITask updatedTask, Period oldPeriod) {
        // Оптимизировать (вызывать reschedule только при действительной необходимости
        reschedule();
    }

    private synchronized void reschedule() {
        LocalDateTime now = LocalDateTime.now();
        if (timerTask != null) {
            timerTask.cancel();
            timer.purge();
            timerTask = null;
        }
        Optional<Map.Entry<ITask, Period>> nextEntry = data.entrySet().stream().
                filter((entry) -> entry.getValue().getStart().isAfter(now)).
                min(Comparator.comparing((entry) -> entry.getValue().getStart()));


        nextEntry.ifPresent((entry) ->
        {
            long millis = java.time.Duration.between(LocalDateTime.now(), entry.getValue().getStart()).toMillis();
            if (millis < 0)
                millis = 0;
            timerTask = new TimerTask(entry.getKey());
            timer.schedule(timerTask, millis);
        });
    }
}

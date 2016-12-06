import subscribers.PopupNotifier;
import tracker.api.IJournal;
import tracker.api.ITask;
import tracker.api.Period;
import tracker.api.PeriodOverlapException;
import tracker.impl.Manager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    //06.12.2016 13:40:20
    public static void main(String[] args) {
        IJournal journal = null;
        try {
            journal = Manager.getInstance().loadJournal(null);
        } catch (IOException e) {
            e.getMessage();
        }
        journal.register(new PopupNotifier());
        StringBuilder message = new StringBuilder("\nWhat do you want with tasks? \n");
        message.append("  - add  \n");
        message.append("  - delete  \n");
        message.append("  - show  \n");
        message.append("  - exit \n");
        message.append("Enter a command: ");
        while (true) {
            System.out.print(message);
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            switch (command) {
                case "add": {
                    System.out.print("\nEnter task name: ");
                    String name = sc.nextLine();
                    System.out.print("\nEnter task description: ");
                    String description = sc.nextLine();
                    while (true) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                            System.out.print("\nEnter start time. Format is DD.MM.YYYY HH:MM:SS: ");
                            LocalDateTime startTime = LocalDateTime.parse(sc.nextLine(), formatter);
                            if (LocalDateTime.now().isAfter(startTime)) {
                                System.out.print("Error! StartTime must be after current time.");
                                continue;
                            }
                            System.out.print("\nEnter End time. Format is DD.MM.YYYY HH:MM:SS: ");
                            LocalDateTime endTime = LocalDateTime.parse(sc.nextLine(), formatter);
                            if (endTime.isBefore(startTime)) {
                                System.out.println("Error! EndTime must be after StarTime.");
                                continue;}
                            addTask(journal, name, description, new Period(startTime, endTime));
                            break;
                        } catch (Exception e) {
                            System.out.println("Error! Please type the time in the right way...\n" + e.getMessage());
                            continue;
                        }
                    }

                    break;
                }
                case "delete": {
                    System.out.print("Write a task name: ");
                    String taskName = sc.nextLine().toLowerCase();
                    journal.getTasks(new Period(LocalDateTime.MIN, LocalDateTime.MAX)).stream().
                            filter((task) -> task.getName().toLowerCase().contains(taskName)).
                            forEach(journal::removeTask);
                    break;
                }

                case "show": {
                    System.out.println("Number of Tasks: " + journal.getNumberOfTasks());
                    System.out.println("================");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                    final IJournal finalJournal = journal;
                    journal.getTasks(new Period(LocalDateTime.MIN, LocalDateTime.MAX)).stream().
                            forEach((task) -> {
                                Period period = finalJournal.getTaskPeriod(task);
                                System.out.println("ID:          " + task.getId());
                                System.out.println("Name:        " + task.getName());
                                System.out.println("Description: " + task.getDescription());
                                System.out.println("StartTime:   " + period.getStart().format(formatter));
                                System.out.println("EndTime:     " + period.getEnd().format(formatter));
                                System.out.println("================");
                            });
                    break;
                }

                case "exit": {
                    try {
                        Manager.getInstance().saveJournal(journal, "temp");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                default:
                    System.out.println("\nThe wrong command! Try again...");
            }
        }
    }

    private static void addTask(IJournal journal, String name, String description, Period period) throws PeriodOverlapException {
        ITask task = journal.createTask(period);
        task.setName(name);
        task.setDescription(description);
    }
}

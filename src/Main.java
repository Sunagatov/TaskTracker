import subscribers.PopupNotifier;
import tracker.api.*;
import tracker.impl.Manager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    //20.12.2016 13:40:20
    public static void main(String[] args) {
        System.out.println("Welcome to the TaskTracker by Zufar! ");
        Scanner sc = new Scanner(System.in);
        IJournal journal = null;
        StringBuilder message1 = new StringBuilder("\nWhat do you want with journal? \n");
        message1.append("  - create \n");
        message1.append("  - load  \n");
        String command;
        while (true) {
            System.out.print(message1);
            command = sc.nextLine();
            switch (command) {
                case "create": {
                    journal = Manager.getInstance().createJournal();
                    System.out.println("\nJournal created successfully.");
                    break;
                }
                case "load": {
                    try {
                        journal = Manager.getInstance().loadJournal("C:\\Users\\ьр\\IdeaProjects\\TaskTracker");
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("\nError! Journal can not be loaded.");
                        continue;
                    }
                    break;
                }
                default: {
                    System.out.println("\nThe wrong command! Try again...");
                    continue;
                }
            }
            break;
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
            command = sc.nextLine();
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
                                continue;
                            }
                            ITask task = journal.createTask(new Period(startTime, endTime));
                            task.setDescription(description);
                            task.setName(name);
                            break;
                        } catch (Exception e) {
                            System.out.println("Error! Please type the time in the right way...\n");
                        }
                    }

                    break;
                }

                case "delete": {
                    System.out.print("Write a task name: ");
                    String taskName = sc.nextLine().toLowerCase();
                    Stream<ITask> s = journal.getTasks(new Period(LocalDateTime.MIN, LocalDateTime.MAX)).stream().
                            filter((task) -> task.getName().toLowerCase().contains(taskName));
                    if (s.count() == 0 || journal.getNumberOfTasks() == 0) {
                        System.out.println("Error! The task with that name is not existed!");
                        continue;
                    }
                    journal.getTasks(new Period(LocalDateTime.MIN, LocalDateTime.MAX)).stream().
                            filter((task) -> task.getName().toLowerCase().contains(taskName)).
                            forEach(journal::removeTask);
                    System.out.println("\nThe Task was removed successfully.");
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
                   /* try {
                        Manager.getInstance().saveJournal(journal, "MyObject.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    //System.out.println("\nJournal was saved successfully.");
                    return;
                }

                default:
                    System.out.println("\nThe wrong command! Try again...");
            }
        }
    }
}

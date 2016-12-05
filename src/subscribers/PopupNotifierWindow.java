package subscribers;

import tracker.api.IJournal;
import tracker.api.ITask;
import tracker.api.Period;
import tracker.api.PeriodOverlapException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class PopupNotifierWindow extends JFrame {

    PopupNotifierWindow(IJournal journal, ITask task) {
        setSize(400, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        setLayout(new FlowLayout());
        setTitle(task.getName());
        JLabel nameLabel = new JLabel();
        nameLabel.setText(task.getDescription());
        JButton closeButton = new JButton();
        closeButton.setText("Close");
        closeButton.addActionListener((actionEvent) -> dispose());
        JButton postponeButton = new JButton();
        postponeButton.setText("Postpone");
        postponeButton.addActionListener((actionEvent) -> {
            // Запросить новый период
            LocalDateTime start = LocalDateTime.now().plusSeconds(5);
            Period old = journal.getTaskPeriod(task);
            long seconds = ChronoUnit.SECONDS.between(old.getStart(), old.getEnd());
            LocalDateTime end = start.plusSeconds(seconds);
            try {
                journal.setTaskPeriod(task, new Period(start, end));
            } catch (PeriodOverlapException e) {
                // Неверный период, запросить снова
            }
            dispose();
        });
        add(nameLabel);
        add(closeButton);
        add(postponeButton);
    }
}

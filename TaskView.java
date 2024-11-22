package toDoList;

import javax.swing.*;
import java.awt.*;

public class TaskView extends JPanel implements ListCellRenderer<Task> {
    private JLabel taskLabel;
    private JLabel dueDateLabel;
    private JCheckBox completedCheckBox;

    public TaskView() {
        setLayout(new BorderLayout());
        taskLabel = new JLabel();
        dueDateLabel = new JLabel();
        completedCheckBox = new JCheckBox();

        add(taskLabel, BorderLayout.WEST);
        add(dueDateLabel, BorderLayout.CENTER);
        add(completedCheckBox, BorderLayout.EAST);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
        if (task != null) {
            taskLabel.setText(task.getDescription());
            dueDateLabel.setText(task.getDueDate().toString());
            completedCheckBox.setSelected(task.isCompleted());
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}

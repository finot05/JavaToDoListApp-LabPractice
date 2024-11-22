package toDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoListApp extends JFrame {
    private TaskOrganizer taskOrganizer;
    private JList<Task> taskJList;
    private DefaultListModel<Task> taskListModel;
    private JTextField taskInput;
    private JTextField dueDateInput;
    private JButton addButton, removeButton, removeAllButton;

    public ToDoListApp() {
        super("To-Do-List App");

        taskOrganizer = new TaskOrganizer();

        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(212, 230, 241));
        setLayout(new BorderLayout());
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.DARK_GRAY);


        taskListModel = new DefaultListModel<>();
        taskJList = new JList<>(taskListModel);
        taskJList.setCellRenderer(new TaskView());

        taskInput = new JTextField("Enter task here...");
        taskInput.setForeground(Color.GRAY);

        taskInput.setFont(new Font("Arial", Font.PLAIN, 20));
        taskInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                taskInput.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 153), 1));


                if (taskInput.getText().equals("Enter task here...")) {
                    taskInput.setText("");
                    taskInput.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (taskInput.getText().isEmpty()) {
                    taskInput.setForeground(Color.GRAY);
                    taskInput.setText("Enter task here...");
                }
            }
        });

        dueDateInput = new JTextField("Due Date (yyyy-MM-dd)");
        dueDateInput.setForeground(Color.GRAY);
        dueDateInput.setFont(new Font("Arial", Font.PLAIN, 20));
        dueDateInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                dueDateInput.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 153), 1));

                if (dueDateInput.getText().equals("Due Date (yyyy-MM-dd)")) {
                    dueDateInput.setText("");
                    dueDateInput.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dueDateInput.getText().isEmpty()) {
                    dueDateInput.setForeground(Color.GRAY);
                    dueDateInput.setText("Due Date (yyyy-MM-dd)");
                }
            }
        });

        addButton = new JButton("Add");
        addButton.setBackground(new Color(130, 224, 170));
        addButton.setOpaque(true);

        removeButton = new JButton("Remove Marked");
        removeButton.setBackground(new Color(241, 148, 138));
        removeButton.setOpaque(true);

        removeAllButton = new JButton("Remove All");
        removeAllButton.setBackground(new Color(241, 148, 138));
        removeAllButton.setOpaque(true);


        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.black);
        inputPanel.setOpaque(true);
        JPanel inputFieldsPanel = new JPanel(new GridLayout(2, 1));
        inputFieldsPanel.add(taskInput);
        inputFieldsPanel.add(dueDateInput);
        inputPanel.add(inputFieldsPanel, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(removeAllButton);

        add(new JScrollPane(taskJList), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        removeAllButton.addActionListener(e -> removeAllTasks());

        dueDateInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addTask();
                }
            }
        });
        taskJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = taskJList.locationToIndex(e.getPoint());
                Task task = taskListModel.getElementAt(index);
                task.setCompleted(!task.isCompleted());
                taskJList.repaint(taskJList.getCellBounds(index, index));
            }
        });

        setVisible(true);
    }

    private void addTask() {
        String description = taskInput.getText().trim();
        String dueDateString = dueDateInput.getText().trim();
        if (!description.isEmpty() && !description.equals("Enter  task here...") && !dueDateString.isEmpty() && !dueDateString.equals("Due Date (yyyy-MM-dd)")) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = dateFormat.parse(dueDateString);
                Task newTask = new Task(description, dueDate);
                taskOrganizer.addTask(newTask);
                taskListModel.addElement(newTask);
                resetInputs();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task description and a due date.");
        }
    }

    private void resetInputs() {
        taskInput.setText("Enter task here...");
        taskInput.setForeground(Color.GRAY);
        dueDateInput.setText("Due Date (yyyy-MM-dd)");
        dueDateInput.setForeground(Color.GRAY);
    }

    private void removeTask() {
        int selectedIndex = taskJList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskOrganizer.removeTask(selectedIndex);
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.");
        }
    }

    private void removeAllTasks() {
        taskOrganizer.removeAllTasks();
        taskListModel.clear();
    }

    public static void main(String[] args) {
        new ToDoListApp();
    }
}

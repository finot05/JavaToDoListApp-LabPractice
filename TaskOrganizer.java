package toDoList;

public class TaskOrganizer {
    private TaskNode head;

    public TaskOrganizer() {
        this.head = null;
    }

    public void addTask(Task task) {
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void removeTask(int index) {
        if (head == null || index < 0) {
            return;
        }
        if (index == 0) {
            head = head.next;
            return;
        }

        TaskNode current = head;
        int i = 0;
        while (current != null && i < index - 1) {
            current = current.next;
            i++;
        }

        if (current == null || current.next == null) {
            return;
        }

        current.next = current.next.next;
    }

    public void removeAllTasks() {
        head = null;
    }

}
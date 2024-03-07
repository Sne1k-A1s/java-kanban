package Manager;

import Entity.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private Node head;
    private Node tail;
    private final Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            Integer taskId = task.getId();
            if (nodeMap.containsKey(taskId)) {
                remove(taskId);
            }
            linkLast(task);
            nodeMap.put(taskId, tail);
        }
    }

    private void linkLast(Task task) {

        if (tail == null) {
            Node newNode = new Node(null, task, null);
            head = newNode;
            tail = newNode;
        } else {
            Node newNode = new Node(this.tail, task, null);
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
   public void remove(int id) {
        removeNode(nodeMap.get(id));
    }
    public void removeNode(Node node) {
        if (node != null) {
            final Node next = node.next;
            final Node prev = node.prev;
            node.object = null;
            if (head == node && tail == node) {
                head = null;
                tail = null;
            } else if (head == node && !(tail == node)) {
                head = next;
                head.prev = null;
            } else if (head != node && tail == node) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
    private List<Task> getTasks() {
        List<Task> task = new LinkedList<>();
        Node node = head;

        while (node != null) {
            task.add(node.object);
            node = node.next;
        }
        return task;
    }
}

package Manager;

import Entity.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;
    private final Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            Integer taskId = task.getId();
            remove(taskId);
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
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
    }

    public void removeNode(Node node) {
        if (node != null) {
            final Node next = node.getNext();
            final Node prev = node.getPrev();
            if (head == node && tail == node) {
                head = null;
                tail = null;
            } else if (head == node && !(tail == node)) {
                head = next;
                head.setPrev(null);
            } else if (head != node && tail == node) {
                tail = prev;
                tail.setNext(null);
            } else {
                prev.setNext(next);
                next.setPrev(prev);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> copyListTask = getTasks();
        return copyListTask;
    }
    private List<Task> getTasks() {
        List<Task> task = new LinkedList<>();
        Node node = head;

        while (node != null) {
            task.add(node.getObject());
            node = node.getNext();
        }
        return task;
    }
}

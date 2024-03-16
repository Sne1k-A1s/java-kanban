package Manager;

import Entity.Task;

public class Node {

    private Node prev;
    private Task object;
    private Node next;

    public Node(Node prev, Task object, Node next) {
        this.prev = prev;
        this.object = object;
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Task getObject() {
        return object;
    }

    public void setObject(Task object) {
        this.object = object;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

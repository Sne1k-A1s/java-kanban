package Manager;

import Entity.Task;

public class Node {
    public Node prev;
    public Task object;
    public Node next;

    public Node(Node prev, Task object, Node next) {
        this.prev = prev;
        this.object = object;
        this.next = next;
    }

}

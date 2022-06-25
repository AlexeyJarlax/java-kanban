package managers;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class InMemoryHistoryManager implements HistoryManager {

    //4/ private List<Task> taskHistory;

    private LinkedList<Node<Task>> taskHistory;
    private HashMap<Integer, Node<Task>> receivedTasks;

    public Node<Task> head;
    public Node<Task> tail;
    private int size = 0;

     public InMemoryHistoryManager() {
         this.taskHistory = new LinkedList<>();

         this.receivedTasks = new HashMap<>(); //5/

     }

    public void linkLast(Task element) {  //5/ реализация двусвязного списка задач с методом linkLast
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<Task>(oldTail, element, null);
        tail = newNode;
        receivedTasks.put(element.getId(), newNode);
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
    }

    public List<Task> getTasks() { //5/ реализация двусвязного списка задач с методом getTasks
        List<Task> tasks = new ArrayList<>();
        Node<Task> currentNode = head;
        while (!(currentNode == null)) {
            tasks.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return tasks;
    }

    public void removeNode(Node<Task> node) {  //5/ метод void remove(int id) для удаления задачи из просмотра (принимает объект Node — узел связного списка и вырезает его)
        if (!(node == null)) {
            final Task data = node.data;
            final Node<Task> next = node.next;
            final Node<Task> prev = node.prev;
            node.data = null;

            if (head == node && tail == node) {
                head = null;
                tail = null;
            } else if (head == node && !(tail == node)) {
                head = next;
                head.prev = null;
            } else if (!(head == node) && tail == node) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }

            node = null;
            size--;

        }

    }

    /* @Override //4/ ретроградный Override
    public void add(Task task) {
        if (!(task == null)) {
            taskHistory.add(task);
            if (taskHistory.size() > 10) {
                taskHistory.remove(0);
            }
        }
    } */

    @Override //5/
    public void add(Task task) {
        if (!(task == null)) {
            remove(task.getId());
            linkLast(task);
        }
    }

    @Override  //5/
    public void remove(int id) {
        removeNode(receivedTasks.get(id));
    }


    @Override
    public List<Task> getHistory() {
        //4/ return taskHistory;
        return getTasks();  //5/
    }
}

    class Node<Task> { //5/ отдельный класс Node для узла списка

        public Task data;
        public Node<Task> next;
        public Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

}
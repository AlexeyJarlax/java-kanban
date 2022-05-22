package managers;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> taskHistory;

    public InMemoryHistoryManager() {
        this.taskHistory = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (!(task == null)) {
            taskHistory.add(task);
            if (taskHistory.size() > 10) {
                taskHistory.remove(0);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }
}
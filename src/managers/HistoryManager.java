package managers;

import tasks.Task;

import java.util.List;

public interface HistoryManager {

    public void add(Task task);
    public void remove(int id); //5/
    public List<Task> getHistory();
}
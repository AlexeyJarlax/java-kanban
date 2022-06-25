package managers;

import tasks.*;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    //задачи
    Task addTask(Task task);

    void updateTask(Task task);

    Task getTask(int id);

    HashMap<Integer, Task> getTasks();

    void deleteTask(int id);

    void deleteAllTasks();

    //эпики
    void addEpic(Epic epic);

    void updateEpic(Epic epic);

    Epic getEpic(int id);

    HashMap<Integer, Epic> getEpics(int i);

    void deleteEpic(int id);

    void deleteAllEpics();

    //подзадачи
    void addSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    Subtask getSubtask(int id);

    HashMap<Integer, Subtask> getSubtasks(int i);

    void deleteSubtask(int id);

    void deleteAllSubtask();

    List<Task> history();

}
package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager { // класс для объекта менеджер
    private int id; //хранение задач для Задач, Подзадач и Эпиков:
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;
    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        id = 0;
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    //Задачи: добавляем task
    @Override
    public Task addTask(Task task) {
        task.setId(++id);
        tasks.put(id, task);
        return task;
    }

    // храним task
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    // извлекаем task
    @Override
    public Task getTask(int id) {
        return tasks.getOrDefault(id, null);
    }

    @Override

     public HashMap<Integer, Task> getTasks() {
     return tasks;
    }

    // метод для удаления
    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    //Эпики пошли
    @Override
    public void addEpic(Epic epic) {
        epic.setId(++id);
        epic.setStatus(String.valueOf(Status.NEW));
        epics.put(id, epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        epic.setEpicSubtasks(epics.get(epic.getId()).getEpicSubtasks());
        epics.put(epic.getId(), epic);
        checkEpicStatus(epic);
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.getOrDefault(id, null);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public HashMap<Integer, Epic> getEpics(int i) {
        return epics;
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epics.remove(id);
            for (Integer subtaskId : epic.getEpicSubtasks()) {
                subtasks.remove(subtaskId);
            }
            epic.setEpicSubtasks(new ArrayList<>());
        }
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    //Аналогично для подзадач
    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(++id);
        subtasks.put(id, subtask);
        subtask.getEpic().getEpicSubtasks().add(id);
        checkEpicStatus(subtask.getEpic());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(subtask.getEpic());
    }

    @Override
    public Subtask getSubtask(int id) {

        Subtask subtask = subtasks.getOrDefault(id, null);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks(int i) {
        return subtasks;
    }

    @Override
    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Epic epic = subtasks.get(id).getEpic();
            epic.getEpicSubtasks().remove((Integer) id);
            checkEpicStatus(epic);
            subtasks.remove(id);
        }
    }

    @Override
    public void deleteAllSubtask() {
        ArrayList<Epic> epicsForStatusUpdate = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            subtask.getEpic().setEpicSubtasks(new ArrayList<>());
            if (!epicsForStatusUpdate.contains(subtask.getEpic())) {
                epicsForStatusUpdate.add(subtask.getEpic());
            }
        }
        subtasks.clear();
        for (Epic epic : epicsForStatusUpdate) {
            epic.setStatus(String.valueOf(Status.NEW));
        }
    }

    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

    // статусы эпиков
    private void checkEpicStatus(Epic epic) {

        if (epic.getEpicSubtasks().size() == 0) {
            epic.setStatus(String.valueOf(Status.NEW));
            return;
        }

        boolean allTaskIsNew = true;
        boolean allTaskIsDone = true;

        for (Integer epicSubtaskId : epic.getEpicSubtasks()) {
            String status = subtasks.get(epicSubtaskId).getStatus();
            if (!status.equals(Status.NEW)) {
                allTaskIsNew = false;
            }
            if (!status.equals(Status.DONE)) {
                allTaskIsDone = false;
            }
        }

        if (allTaskIsDone) {
            epic.setStatus(String.valueOf(Status.DONE));
        } else if (allTaskIsNew) {
            //epic.setStatus("NEW");
            epic.setStatus(String.valueOf(Status.NEW));
        } else {
            //epic.setStatus("IN_PROGRESS");
            epic.setStatus(String.valueOf(Status.IN_PROGRESS));
        }

    }

}
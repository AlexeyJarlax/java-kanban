import tasks.Epic;
import tasks.Subtask;
import managers.Managers;
import managers.TaskManager;
import tasks.Status;
import tasks.Task;
import static managers.TaskManager.*;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        manager.addTask(new Task("Задача №1", "Чай", (String.valueOf(Status.DONE)))); //5/ *отметка для новых строк из 5 спринта* Добавим для отображения статистики простых Задач
        manager.addTask(new Task("Задача №2", "Кофе", (String.valueOf(Status.NEW))));
        manager.addTask(new Task("Задача №3", "Сода", (String.valueOf(Status.NEW))));
        manager.addTask(new Task("Задача №4", "Хлебушек", (String.valueOf(Status.DONE))));

        Epic epic1 = new Epic("Эпик №1", "День кота");
        manager.addEpic(epic1);

        Subtask subtask11 = new Subtask("Эпик1 Подзадача1", "кормление", (String.valueOf(Status.DONE)), epic1);
        manager.addSubtask(subtask11);
        Subtask subtask12 = new Subtask("Эпик1 Подзадача2", "сон", (String.valueOf(Status.IN_PROGRESS)), epic1);
        manager.addSubtask(subtask12);
        Subtask subtask13 = new Subtask("Эпик1 Подзадача3", "чесание", (String.valueOf(Status.NEW)), epic1);
        manager.addSubtask(subtask13);
        Subtask subtask12New = new Subtask("Эпик1 Подзадача2 изменена", "тыгыдык", (String.valueOf(Status.NEW)), epic1);
        subtask12New.setId(12);
        manager.updateSubtask(subtask12New);

        Epic epic2 = new Epic("Эпик №2", "ЯндексПрактикум");
        manager.addEpic(epic2);

        System.out.println("\n Пересчет Эпиков : \n" + manager.getEpics(1));
        System.out.println("\n Пересчет Задач : \n" +  manager.getTasks()); //5/ выводим на экран все виды задач
        System.out.println("\n Пересчет Подзадач : \n" + manager.getSubtasks(1));

        manager.deleteAllEpics();
        manager.deleteAllTasks(); //5/ удаляем все задачи
        manager.deleteAllSubtask();

        System.out.println("\n Итого Эпиков : \n" + manager.getEpics(1));
        System.out.println("\n Итого Задач : \n" +  manager.getTasks()); //5/ проверяем что осталось
        System.out.println("\n Итого Подзадач : \n" + manager.getSubtasks(1));

    }

}
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Epic epic1 = new Epic("Эпик №1", "Жизнь");
        manager.addEpic(epic1);

        Subtask subtask11 = new Subtask("Эпик1 Подзадача1", "Рождение", "Выполнено!", epic1);
        manager.addSubtask(subtask11);
        Subtask subtask12 = new Subtask("Эпик1 Подзадача2", "Старение", "В процессе выполнения...", epic1);
        manager.addSubtask(subtask12);
        Subtask subtask13 = new Subtask("Эпик1 Подзадача3", "Размножение", "Новая", epic1);
        manager.addSubtask(subtask13);
        Subtask subtask12New = new Subtask("Эпик1 Подзадача2 изменена", "Молодение", "Новая", epic1);
        subtask12New.setId(12);
        manager.updateSubtask(subtask12New);

        Epic epic2 = new Epic("Эпик №2", "ЯндексПрактикум");
        manager.addEpic(epic2);

        Subtask subtask21 = new Subtask("Эпик2 Подзадача1", "Спринт № 1, 2, 3", "Выполнено!", epic2);
        manager.addSubtask(subtask21);



        System.out.println("Эпик = " + manager.getEpics());
        System.out.println("подзадача = " + manager.getSubtasks());


    }
}
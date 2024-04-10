package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;

class TaskTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void areTheIdsOfTheTasksEqual() {
        Task task = new Task("Имя", "Описание", Status.NEW, 30, LocalDateTime.now());
        int id = taskManager.addNewTask(task);

        Task taskId = taskManager.getTask(id);
        int ids = taskManager.getId();
        Task task1 = taskManager.getTask(ids);

        assertSame(taskId, task1);
    }

    @Test
    void areTheIdsOfTheTasksEq33ual() {
        LocalDateTime time = LocalDateTime.now();
        Epic epic2 = new Epic("epic1", "задача эпика1", Status.NEW, 30, time);
        int epicId2 = taskManager.addNewEpic(epic2);

        Subtask subtask11 = new Subtask("субстаск1 эпика1", "задача субтаска1", Status.NEW,
                30, time, epicId2);
        taskManager.addNewSubtask(subtask11);

        Subtask subtask12 = new Subtask("субстаск2 эпика1", "задача субтаска2", Status.NEW,
                30, time.plusMinutes(78), epicId2);
        taskManager.addNewSubtask(subtask12);

        Subtask subtask14 = new Subtask("субстаск5 эпика5", "задача субтаска5", Status.NEW,
                30, time, epicId2);
        taskManager.addNewSubtask(subtask14);

        Subtask subtask13 = new Subtask("субстаск3 эпика1", "задача субтаска3", Status.NEW,
                30, time.plusMinutes(47), epicId2);
        taskManager.addNewSubtask(subtask13);

        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

    }
}
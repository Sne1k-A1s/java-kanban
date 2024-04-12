package Test;

import Entity.Status;
import Entity.Task;
import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    @Test
    void areTheIdsOfTheTasksEqual() {
        Task task = new Task("Имя", "Описание", Status.NEW);
        int id = taskManager.addNewTask(task);

        Task taskId = taskManager.getTask(id);
        int ids = taskManager.getId();
        Task task1 = taskManager.getTask(ids);

        assertSame(taskId, task1);
    }
}
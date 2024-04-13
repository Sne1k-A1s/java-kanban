package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
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
}

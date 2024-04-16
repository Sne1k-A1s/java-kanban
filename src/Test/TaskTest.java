package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void addingStartOfExecutionAndDuration() {
        LocalDateTime time = LocalDateTime.now();

        Epic epic = new Epic("эпик", "Описание", Status.NEW);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask2 = new Subtask("сабтаск", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask2);
        Subtask subtask3 = new Subtask("сабтаск", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask3);
        Subtask subtask4 = new Subtask("сабтаск", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask4);

        Subtask subtask5 = new Subtask("сабтаск", "Описание", Status.NEW, 30, time, epicId);
        taskManager.addNewSubtask(subtask5);

        subtask2.setStartTime(time.plusMinutes(40));
        subtask2.setDuration(Duration.ofMinutes(20));

        assertNotNull(subtask2.getStartTime());
        assertNotNull(subtask2.getDuration());
    }

    @Test
    void addingStartOfExecuti78onAndDuration() {
        LocalDateTime time = LocalDateTime.now();

        Epic epic = new Epic("эпик", "Описание", Status.NEW);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask2 = new Subtask("сабтаск", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask2);
        Subtask subtask3 = new Subtask("сабтаск", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask3);
        Subtask subtask4 = new Subtask("сабтаск", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask4);

        Subtask subtask5 = new Subtask("сабтаск", "Описание", Status.NEW, 30, time, epicId);
        taskManager.addNewSubtask(subtask5);

        subtask2.setStartTime(time.plusMinutes(32));
        subtask2.setDuration(Duration.ofMinutes(20));
        taskManager.updateSubtask(subtask2);

        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getPrioritizedTasks());
    }
}

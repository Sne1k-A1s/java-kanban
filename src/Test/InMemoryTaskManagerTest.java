package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    LocalDateTime time = LocalDateTime.now();
    int mm = 30;

    @Override
    public InMemoryTaskManager createTaskManager()  {
        return new InMemoryTaskManager();
    }

    @Test
    void addingTasksOfDifferentTypesAndSearchingByID()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewTask(task);
        Task task1 = taskManager.getTask(1);

        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);
        taskManager.getId();
        Epic epic1 = taskManager.getEpic(2);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time.plusMinutes(40), epicId);
        taskManager.addNewSubtask(subtask);
        taskManager.getId();
        Subtask subtask1 = taskManager.getSubtask(3);

        assertSame(task, task1);
        assertSame(epic, epic1);
        assertSame(subtask, subtask1);
    }
}
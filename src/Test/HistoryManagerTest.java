package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import Manager.HistoryManager;
import Manager.InMemoryTaskManager;
import Manager.Managers;
import Manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryManagerTest {
    HistoryManager historyManager;
    TaskManager taskManager;
    LocalDateTime time = LocalDateTime.now();
    int mm = 30;

    @BeforeEach
    void createHistoryManager()  {
        historyManager = Managers.getDefaultHistory();
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void checkingForAWorkingInstance()  {
        assertNotNull(Managers.getDefault());
        assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    void add()  {
        assertEquals(0, taskManager.getHistory().size());

        Duration duration = Duration.ofMinutes(mm);
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        int id = taskManager.addNewTask(task);

        Task task1 = new Task("Имя1", "Описание", Status.NEW, mm, time.plusMinutes(40));
        int id1 = taskManager.addNewTask(task1);

        taskManager.getTask(id);
        taskManager.getTask(id1);
        taskManager.getTask(id);
        taskManager.getTask(id1);
        System.out.println(taskManager.getHistory());

        assertEquals(2, taskManager.getHistory().size());
    }

    @Test
    void remove()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        int taskId = taskManager.addNewTask(task);
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time.plusMinutes(mm));
        int epicId = taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time.plusMinutes(40), epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);

        historyManager.remove(taskId);
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        historyManager.remove(subtaskId);
        history = historyManager.getHistory();
        assertEquals(1, history.size());
    }
}
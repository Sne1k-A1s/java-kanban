package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Task;
import Manager.InMemoryTaskManager;
import Manager.Managers;
import Manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
   static InMemoryTaskManager taskManager = new InMemoryTaskManager();

//убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    void checkingForSavingTasks() {
        Task task = new Task("Имя", "Описание", Status.NEW);
        int id = taskManager.addNewTask(task);
        Task task1 = new Task("Имя1", "Описание", Status.NEW);
        int id1 = taskManager.addNewTask(task1);

        taskManager.getTask(id);
        taskManager.getTask(id1);
        taskManager.getTask(id);
        taskManager.getTask(id1);
        System.out.println(taskManager.getHistory());

        assertEquals(2, taskManager.getHistory().size());
    }
    @Test
    void checkingForAWorkingInstance() {
        assertNotNull(Managers.getDefault());
        assertNotNull(Managers.getDefaultHistory());
    }
}
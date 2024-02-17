package Test;

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

        taskManager.getTask(id);
        taskManager.getTask(id);

        assertEquals(2, taskManager.getHistory().size());
    }
    @Test
    void checkingForAWorkingInstance() {
        assertNotNull(Managers.getDefault());
        assertNotNull(Managers.getDefaultHistory());
    }
}
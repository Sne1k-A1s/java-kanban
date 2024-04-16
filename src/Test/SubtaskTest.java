package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Manager.InMemoryHistoryManager;
import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class SubtaskTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    LocalDateTime time = LocalDateTime.now();
    int mm = 30;
    @Test
    void areTheIdsOfTheSubtasksEqual()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int idEpic = taskManager.addNewEpic(epic);
        taskManager.getEpic(idEpic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, idEpic);
        int id = taskManager.addNewSubtask(subtask);
        Subtask subtaskId = taskManager.getSubtask(id);
        int id1 = taskManager.getId();
        Subtask subtaskId1 = taskManager.getSubtask(id1);

        assertSame(subtaskId, subtaskId1);
    }
    @Test
    void subtasksCannotBeMadeYourOwnEpic()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm * 2, time);
        taskManager.addNewEpic(epic);
        int epicId = taskManager.getId();

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);
        int subtaskId = taskManager.getId();

        LocalDateTime newTime = time.plusMinutes(mm);
        Subtask subtask1 = new Subtask("Имя", "Описание", Status.NEW, mm, newTime, subtaskId);

        assertNull(subtask1.getId());
    }

    @Test
    void whenDeletingASubtaskTheIdIsAlsoDeleted()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewEpic(epic);
        int epicId = taskManager.getId();

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);
        int subtaskId = taskManager.getId();

        Subtask subtask1 = new Subtask("Имя1", "Описание1", Status.NEW,  mm, time.plusMinutes(40), epicId);
        taskManager.addNewSubtask(subtask1);

        taskManager.deleteSubtask(subtaskId);

        assertNull(taskManager.getSubtask(subtaskId));
    }
}
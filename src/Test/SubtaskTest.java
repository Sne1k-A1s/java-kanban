package Test;

import Entity.Status;
import Entity.Epic;
import Entity.Subtask;
import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void areTheIdsOfTheSubtasksEqual() {
        Epic epic = new Epic("Имя", "Описание", Status.NEW);
        int idEpic = taskManager.addNewEpic(epic);
        taskManager.getEpic(idEpic);
        int ids = taskManager.getId();

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, ids);
        int id = taskManager.addNewSubtask(subtask);
        Subtask subtaskId = taskManager.getSubtask(id);
        int id1 = taskManager.getId();
        Subtask subtaskId1 = taskManager.getSubtask(id1);
        assertSame(subtaskId, subtaskId1);
    }
    @Test
    void subtasksCannotBeMadeYourOwnEpic() {
        Epic epic = new Epic("Имя", "Описание", Status.NEW);
        taskManager.addNewEpic(epic);
        int epicId = taskManager.getId();

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask);
        int subtaskId = taskManager.getId();

        Subtask subtask1 = new Subtask("Имя", "Описание", Status.NEW, subtaskId);

        assertNull(subtask1.getId());

    }
}
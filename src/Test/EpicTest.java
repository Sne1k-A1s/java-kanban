package Test;

import Entity.Status;
import Entity.Epic;
import Entity.Subtask;

import Manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    //Проверка на равенство Эпиков через ID
    @Test
    void areTheIdsOfTheEpicsEqual() {
        Epic epic = new Epic("Имя", "Описание", Status.NEW);
        int id = taskManager.addNewEpic(epic);

        Epic epicId = taskManager.getEpic(id);
        int ids = taskManager.getId();
        Epic epicId1 = taskManager.getEpic(ids);

        assertSame(epicId, epicId1);
    }
    //объект Epic нельзя добавить в самого себя в виде подзадачи;
    @Test
    void AddingAnEpicToYourself() {
        Epic epic = new Epic("Имя", "Описание", Status.NEW);
        taskManager.addNewEpic(epic);
        int epicId = taskManager.getId();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask);
        epic.addSubtaskId(epicId);

        assertEquals(list, epic.getSubtaskId());
    }

    @Test
    void whenDeletingAEpicSubtaskTheIdIsAlsoDeleted() {
        Epic epic = new Epic("Имя", "Описание", Status.NEW);
        taskManager.addNewEpic(epic);
        int epicId = taskManager.getId();

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask);
        int subtaskId = taskManager.getId();

        Subtask subtask1 = new Subtask("Имя1", "Описание1", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask1);

        taskManager.deleteSubtask(subtaskId);

        assertEquals(taskManager.getSubtasks(), taskManager.getEpicSubtask(epicId));
    }
}
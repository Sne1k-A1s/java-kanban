package Test;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import Manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static Entity.Status.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class TaskManagerTest<T extends TaskManager>  {
    T taskManager;

    LocalDateTime time = LocalDateTime.now();
    int mm = 30;
    public abstract T createTaskManager();

    @BeforeEach
    public void BeforeEach()  {
        taskManager = createTaskManager();
    }

    @Test
    void addNewTask()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewTask(task);

        List<Task> task1 = new ArrayList<>();
        task1.add(task);

        List<Task> tasks = taskManager.getTasks();
        assertEquals(task1, tasks);
    }

    @Test
    void addNewEpic()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewEpic(epic);

        List<Epic> epic1 = new ArrayList<>();
        epic1.add(epic);

        List<Epic> epics = taskManager.getEpics();
        assertEquals(epic1, epics);
    }

    @Test
    void addNewSubtask()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);

        List<Subtask> subtask1 = new ArrayList<>();
        subtask1.add(subtask);

        List<Subtask> subtasks = taskManager.getSubtasks();
        assertEquals(subtask1, subtasks);
    }

    @Test
    void getTasks()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewTask(task);

        List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
    }

    @Test
    void getEpics()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewEpic(epic);

        List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics);
    }

    @Test
    void getSubtasks()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);

        List<Subtask> subtasks = taskManager.getSubtasks();

        assertNotNull(subtasks);
    }

    @Test
    void updateTask()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewTask(task);
        task.setStatus(IN_PROGRESS);
        taskManager.updateTask(task);

        assertEquals(IN_PROGRESS, task.getStatus());
    }

    @Test
    void updateEpic() {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);

        subtask.setStatus(IN_PROGRESS);
        taskManager.updateSubtask(subtask);

        assertEquals(IN_PROGRESS, epic.getStatus());
    }

    @Test
    void updateSubtask() {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        final int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);

        subtask.setStatus(IN_PROGRESS);
        taskManager.updateSubtask(subtask);

        assertEquals(IN_PROGRESS, subtask.getStatus());
    }

    @Test
    void getTask()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        int taskId = taskManager.addNewTask(task);

        assertEquals(1, taskId);
    }

    @Test
    void getSubtask()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        assertEquals(2, subtaskId);
    }

    @Test
    void getEpic()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewTask(task);

        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        assertEquals(2, epicId);
    }

    @Test
    void deleteTasks()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewTask(task);

        Task task1 = new Task("Имя", "Описание", Status.NEW, mm, time.plusMinutes(mm));
        taskManager.addNewTask(task1);

        taskManager.deleteTask();
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void deleteEpics()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);

        Subtask subtask1 = new Subtask("Имя", "Описание", Status.NEW, mm, time.plusMinutes(20), epicId);
        taskManager.addNewSubtask(subtask1);

        taskManager.deleteEpic();

        assertEquals(0, taskManager.getEpics().size());
    }

    @Test
    void deleteSubtasks()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);

        Subtask subtask1 = new Subtask("Имя", "Описание", Status.NEW, mm, time.plusMinutes(mm), epicId);
        taskManager.addNewSubtask(subtask1);

        taskManager.deleteSubtask();

        assertEquals(0, taskManager.getSubtasks().size());
    }

    @Test
    void deleteTask()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        int id = taskManager.addNewTask(task);

        Task task1 = new Task("Имя", "Описание", Status.NEW, mm, time.plusMinutes(40));
        taskManager.addNewTask(task1);

        taskManager.deleteTask(id);
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void deleteEpic()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Epic epic1 = new Epic("Имя", "Описание", Status.NEW, mm, time.plusMinutes(mm));
        taskManager.addNewEpic(epic1);

        taskManager.deleteEpic(epicId);

        assertEquals(1, taskManager.getEpics().size());
    }

    @Test
    void deleteSubtask()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        int id = taskManager.addNewSubtask(subtask);

        Subtask subtask1 = new Subtask("Имя", "Описание", Status.NEW, mm, time.plusMinutes(40), epicId);
        taskManager.addNewSubtask(subtask1);

        taskManager.deleteSubtask(id);

        assertEquals(1, taskManager.getSubtasks().size());
    }

    @Test
    void getEpicSubtask()  {
        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time, epicId);
        taskManager.addNewSubtask(subtask);

        Subtask subtask1 = new Subtask("Имя", "Описание", Status.NEW, mm, time.plusMinutes(40), epicId);
        taskManager.addNewSubtask(subtask1);

        ArrayList<Subtask> subtaskList = taskManager.getEpicSubtask(epicId);

        assertEquals(2, subtaskList.size());
    }

    @Test
    void getHistory()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        int taskId = taskManager.addNewTask(task);
        taskManager.getTask(taskId);

        Epic epic = new Epic("Имя", "Описание", Status.NEW, mm, time);
        int epicId = taskManager.addNewEpic(epic);
        taskManager.getEpic(epicId);

        Subtask subtask = new Subtask("Имя", "Описание", Status.NEW, mm, time.plusMinutes(40), epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);
        taskManager.getSubtask(subtaskId);

        List<Task> history = new ArrayList<>();
        history.add(task);
        history.add(epic);
        history.add(subtask);

        assertEquals(history, taskManager.getHistory());
    }

    @Test
    void getPrioritizedTasks()  {
        Task task = new Task("Имя", "Описание", Status.NEW, mm, time);
        taskManager.addNewTask(task);

        Task task1 = new Task("Имя", "Описание", Status.NEW, mm, time.plusMinutes(31));
        taskManager.addNewTask(task1);

        Task task2 = new Task("Имя", "Описание", Status.NEW, mm, time.plusMinutes(62));
        taskManager.addNewTask(task2);

        Task task3 = new Task("Имя", "Описание", Status.NEW, mm, time.plusMinutes(93));
        taskManager.addNewTask(task3);

        SortedSet<Task> list = taskManager.getPrioritizedTasks();
        TreeSet<Task> listSample = new TreeSet<>();
        listSample.add(task);
        listSample.add(task2);
        listSample.add(task3);
        listSample.add(task1);

        assertEquals(listSample, list);
    }
}
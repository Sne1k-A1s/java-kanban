package Manager;
import Entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public interface TaskManager {

    int addNewTask(Task task);

    int addNewSubtask(Subtask subtask);

    int addNewEpic(Epic epic);

    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Epic> getEpics();

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    void deleteTask(int id);

    void deleteTask();

    void deleteSubtask();

    void deleteEpic();

    void deleteSubtask(int id);

    void deleteEpic(int id);

    ArrayList<Subtask> getEpicSubtask(int epicId);

    List<Task> getHistory();

    SortedSet<Task> getPrioritizedTasks();
}
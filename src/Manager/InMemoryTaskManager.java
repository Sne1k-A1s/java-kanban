package Manager;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int generateId = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();


    @Override
    public int addNewTask(Task task) {
        final int id = ++generateId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }
    @Override
    public int addNewSubtask(Subtask subtask) {
        final int id = ++generateId;
        subtask.setId(id);
        int epicId = subtask.getEpicId();
        epics.get(epicId).addSubtaskId(id);
        subtasks.put(id, subtask);
        return id;
    }
    @Override
    public int addNewEpic(Epic epic) {
        final int id = ++generateId;

        epic.setId(id);
        epics.put(id, epic);
        return id;
    }
    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>(tasks.values());
        return taskArrayList;
    }
    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> subtaskArrayList = new ArrayList<>(subtasks.values());
        return subtaskArrayList;
    }
    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicArrayList = new ArrayList<>(epics.values());
        return epicArrayList;
    }
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubtask1(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Integer epicId = subtask.getEpicId();
        updateStatusOfEpic(epics.get(epicId));
    }
    @Override
    public void updateEpic(Epic epic) {
        int idEpic = epic.getId();
        ArrayList<Integer> subtaskId = epics.get(idEpic).getSubtaskId();

        epic.setSubtaskId(subtaskId);
        epics.put(epic.getId(), epic);
        updateStatusOfEpic(epic);
    }
    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }
    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }
    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }
    @Override
    public void deleteTask() {
        tasks.clear();
    }
    @Override
    public void deleteSubtask() {
        subtasks.clear();
        for (Integer id : epics.keySet()) {
                epics.get(id).cleanSubtaskId();
                updateStatusOfEpic(epics.get(id));
        }
    }
    @Override
    public void deleteEpic() {
        for (Integer id : epics.keySet()) {
                epics.get(id).cleanSubtaskId();
        }
        subtasks.clear();
        epics.clear();
    }
    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }
    @Override
    public void deleteSubtask(int id) {
        int idEpic = subtasks.get(id).getEpicId();
        epics.get(idEpic).removeSubtaskId(id);
        subtasks.remove(id);
        updateStatusOfEpic(epics.get(idEpic));
    }
    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        for (Integer idSubtask : epic.getSubtaskId()) {
            subtasks.remove(idSubtask);
        }
        epic.cleanSubtaskId();
        epics.remove(id);
    }
    @Override
    public ArrayList<Subtask> getEpicSubtask(int epicId) {
        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (Integer idSubtask : epic.getSubtaskId()) {
            subtaskArrayList.add(subtasks.get(idSubtask));
        }
        return subtaskArrayList;
    }
    @Override
    public Subtask getSubtask(Integer id) {
        Subtask subtask = subtasks.get(id);
        return subtask;
    }

    public void updateStatusOfEpic(Epic epic) {

        boolean isStatusNew = true;
        boolean isStatusDone = true;
            for (Integer idSubtask : epic.getSubtaskId()) {
                Subtask subtask = getSubtask(idSubtask);
                if (!subtask.getStatus().equals(Status.NEW)) {
                    isStatusNew = false;
                }
                if (!subtask.getStatus().equals(Status.DONE)) {
                    isStatusDone = false;
                }
            }
            if (isStatusNew) {
                epic.setStatus(Status.NEW);
            } else if (isStatusDone) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
            System.out.println(epic.getStatus());
    }

    @Override
    public List<Task> getHistory() {
                return historyManager.getHistory();
    }

    public List<Task> getDefaultHistory() {
        return historyManager.getHistory();
    }

    public Integer getId() {
        return generateId;
    }

}


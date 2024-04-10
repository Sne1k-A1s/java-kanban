package Manager;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.time.LocalDateTime;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected int generateId = 0;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    protected TreeSet<Task> treeSet = new TreeSet<>();

    @Override
    public int addNewTask(Task task) {
        final int id = ++generateId;

        task.setId(id);
        if ((task.getStartTime() != null) && (getBooleanAsks(task))) {
            System.out.println("Задача не создана, время задач пересекаются");
            return --generateId;
        }

        tasks.put(id, task);
        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        final int id = ++generateId;

        if ((subtask.getStartTime() != null) && (getBooleanAsks(subtask))) {
            System.out.println("Задача не создана, время задач пересекаются");
            return --generateId;
        }
        subtask.setId(id);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        epic.addSubtaskId(id);
        subtasks.put(id, subtask);
        if (subtask.getStartTime() != null) {
            if (epic.getSubtaskId().size() == 1) {
                epic.setDuration(subtask.getDuration());
                epic.setStartTime(subtask.getStartTime());
                epic.setEndTime(subtask.getEndTime());
            } else {
                updateTimeEpic(epic);
            }
        }
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
        if (getBooleanAsks(task)) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Время начала задачи уже занято");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask.getStartTime() == null) {
            subtasks.put(subtask.getId(), subtask);
            Integer epicId = subtask.getEpicId();
            updateStatusOfEpic(epics.get(epicId));
        } else if (!getBooleanAsks(subtask)) {
            System.out.println("Время начала задачи уже занято");
        } else {
            subtasks.put(subtask.getId(), subtask);
            Integer epicId = subtask.getEpicId();
            updateStatusOfEpic(epics.get(epicId));
            updateTimeEpic(epics.get(epicId));
        }
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
        var task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        var subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        var epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public void deleteTask() {
        for (Integer Id : tasks.keySet()) {
            historyManager.remove(Id);
        }
        tasks.clear();
    }

    @Override
    public void deleteEpic() {
        deleteSubtask();
        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteSubtask() {
        for (Integer Id : subtasks.keySet()) {
            historyManager.remove(Id);
        }
        subtasks.clear();
        for (Integer id : epics.keySet()) {
                epics.get(id).cleanSubtaskId();
                updateStatusOfEpic(epics.get(id));

        }
    }

    @Override
    public void deleteTask(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        for (Integer idSubtask : epic.getSubtaskId()) {
            subtasks.remove(idSubtask);
            historyManager.remove(idSubtask);
        }
        epic.cleanSubtaskId();
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        int idEpic = subtasks.get(id).getEpicId();
        epics.get(idEpic).removeSubtaskId(id);
        subtasks.remove(id);
        updateStatusOfEpic(epics.get(idEpic));
        historyManager.remove(id);
        updateTimeEpic(epics.get(id));
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

    public Subtask getSubtask(Integer id) {
        Subtask subtask = subtasks.get(id);
        return subtask;
    }

    private void updateStatusOfEpic(Epic epic) {

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

    public Integer getId() {
        return generateId;
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        treeSet.clear();
        treeSet.addAll(tasks.values());
        treeSet.addAll(subtasks.values());
        return treeSet;
    }

    public Boolean getBooleanAsks(Task task) {
        LocalDateTime taskEndTime = task.getEndTime();
        LocalDateTime taskStartTime = task.getStartTime();
        if (getPrioritizedTasks().isEmpty() || taskEndTime == null) return false;
        for (Task prioritizedTask : getPrioritizedTasks()) {
            if (task.equals(prioritizedTask)) continue;
            LocalDateTime prioritizedTaskStartTime = prioritizedTask.getStartTime();
            LocalDateTime prioritizedTaskEndTime = prioritizedTask.getEndTime();
            if (!(prioritizedTaskStartTime == null) && !(taskStartTime == null) &&
                    taskEndTime.isAfter(prioritizedTaskStartTime) &&
                    taskStartTime.isBefore(prioritizedTaskEndTime)) {
                return true;
            }
        }
        return false;
    }

    public void updateTimeEpic(Epic epic) {
        if (epic.getStartTime() != null) {
            for (Integer id : epic.getSubtaskId()) {
                if (epic.getStartTime().isAfter(subtasks.get(id).getStartTime())) {
                    epic.setStartTime(subtasks.get(id).getStartTime());
                }
                if (epic.getEndTime().isBefore(subtasks.get(id).getEndTime())) {
                    epic.setEndTime(subtasks.get(id).getEndTime());
                }
                epic.setDuration(Duration.between(epic.getStartTime(), epic.getEndTime()));
            }
        }
    }
}


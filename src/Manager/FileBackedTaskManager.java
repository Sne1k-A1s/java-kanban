package Manager;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import exceptions.ManagerSaveException;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private Path path;

    public FileBackedTaskManager(Path path) {
        this.path = path;
    }

    @Override
    public int addNewTask(Task task) {
       int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int id = super.addNewSubtask(subtask);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public Task getTask(int id) {
        save();
        return super.getTask(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        save();
        return super.getSubtask(id);
    }

    @Override
    public Epic getEpic(int id) {
        save();
        return super.getEpic(id);
    }

    @Override
    public void deleteTask() {
        super.deleteTask();
        save();
    }

    @Override
    public void deleteSubtask() {
        super.deleteSubtask();
        save();
    }

    @Override
    public void deleteEpic() {
        super.deleteEpic();
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public ArrayList<Subtask> getEpicSubtask(int epicId) {
        save();
        return super.getEpicSubtask(epicId);
    }

    @Override
    public List<Task> getHistory() {
        save();
        return super.getHistory();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(path.toFile(), false)) {

            writer.write("id,type,name,status,description,epic\n");
            for (Task task : tasks.values()) {
                String lineTask = toString(task);
                writer.write(lineTask);
            }
            for (Task epic : epics.values()) {
                writer.write(toString(epic));
            }
            for (Task subtask : subtasks.values()) {
                writer.write(toString(subtask));
            }
            writer.write("\n");
            writer.write(historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Что-то пошло не так");
        }
    }

    private String toString(Task task) {
        if (task.getType() == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task;
            return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + "," + subtask.getEpicId() + "\n";
        }
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + ","
                + task.getDescription() + "\n";
    }

    public static String historyToString(HistoryManager manager) {
        String lineId = "";
        List<Task> taskHistory = manager.getHistory();
        for (int i = 0; i < taskHistory.size(); i++) {
            if (i == taskHistory.size() - 1) {
                lineId = lineId + taskHistory.get(i).getId();
            } else {
                lineId = lineId + taskHistory.get(i).getId() + ", ";
            }
        }
        return lineId;
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> taskId = new ArrayList<>();
        String[] historyIdTask = value.split(",");
        for (String id : historyIdTask) {
            int idTask = Integer.valueOf(id);
            taskId.add(idTask);
        }
        return taskId;
    }

    public static Task fromString(String value) {
        String[] lineTask = value.split(",", 5);

        int id = Integer.valueOf(lineTask[0]);
        TaskType type = TaskType.valueOf(lineTask[1]);
        String name = lineTask[2];
        Status status = Status.valueOf(lineTask[3]);
        String description = lineTask[4];

        switch (type) {
            case TASK:
                return new Task(id, type, name, description, status);
            case EPIC:
                return new Epic(id, type, name, description, status);
            case SUBTASK:
                String epicId = lineTask[5].trim();
                return new Subtask(id, type, name, description, status, Integer.valueOf(epicId));
            default:
                return null;
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManagers = new FileBackedTaskManager(file.toPath());

        String fileContent = readFileContentsOrNull(file.getAbsolutePath());
        String[] lines = fileContent.split("\n");
        int maxId = 0;
        if (lines.length != 0) {
            for (int i = 1; i < lines.length - 2; i++) {
                Task task = fromString(lines[i]);
                TaskType taskType = task.getType();
                Epic epic;
                Subtask subtask;

                switch (taskType) {
                    case TASK:
                        taskManagers.tasks.put(task.getId(), task);
                        if (task.getId() > maxId) {
                            maxId = task.getId();
                        }
                        break;
                    case EPIC:
                        epic = (Epic) task;
                        taskManagers.epics.put(epic.getId(), epic);
                        if (epic.getId() > maxId) {
                            maxId = epic.getId();
                        }
                        break;
                    case SUBTASK:
                        subtask = (Subtask) task;
                        Integer epicId = subtask.getEpicId();
                        taskManagers.subtasks.put(subtask.getId(), subtask);
                        Epic epicNotNull = taskManagers.epics.get(epicId);
                        if (epicNotNull != null) {
                            epicNotNull.addSubtaskId(subtask.getId());
                        }
                        if (subtask.getId() > maxId) {
                            maxId = subtask.getId();
                        }
                        break;
                }
            }
            taskManagers.generateId = maxId;
            List<Integer> idTasks = new ArrayList<>(historyFromString(lines[lines.length - 1]));
            for (Integer id : idTasks) {
                var epic = taskManagers.epics.get(id);
                var subtask = taskManagers.subtasks.get(id);
                var task = taskManagers.tasks.get(id);
               if (epic != null) {
                    taskManagers.historyManager.add(epic);
                } else if (subtask != null) {
                    taskManagers.historyManager.add(subtask);
                } else if (task != null) {
                    taskManagers.historyManager.add(task);
                }
            }
        }
        return taskManagers;
    }

    public static String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}

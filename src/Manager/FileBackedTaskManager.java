package Manager;

import Entity.Epic;
import Entity.Status;
import Entity.Subtask;
import Entity.Task;
import exceptions.ManagerSaveException;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final Path path;

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

            writer.write("id,type,name,description,status,duration,startTime,endTime/epicId\n");
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
            if (task.getStartTime() == null) {
                return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getDescription() + ","
                        + task.getStatus() + "," + subtask.getEpicId() + "\n";
            }
            return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getDescription() + ","
                    + task.getStatus() + "," + task.getDuration().toMinutes() + "," + task.getStringStartTime() + ","
                    + subtask.getEpicId() + "\n";
        } else if (task.getType() == TaskType.EPIC) {
            if (task.getStartTime() == null) {
                return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getDescription() + ","
                        + task.getStatus() + "\n";
            }
            return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getDescription() + ","
                    + task.getStatus() + "," + task.getDuration().toMinutes() + "," + task.getStringStartTime() + ","
                    + task.getStringEndTime() + "\n";
        }
        if (task.getStartTime() == null) {
            return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getDescription() + ","
                    + task.getStatus() + "\n";
        }
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getDescription() + ","
                + task.getStatus() + "," + task.getDuration().toMinutes() + "," + task.getStringStartTime() + "\n";
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
        if(value != null) {
            String[] historyIdTask = value.split(",");
            for (String id : historyIdTask) {
                int idTask = Integer.valueOf(id);
                taskId.add(idTask);
            }
        }
        return taskId;
    }

    public static Task fromString(String value) {
        String[] lineTask = value.split(",", 8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm");

        int id = Integer.valueOf(lineTask[0]);
        TaskType type = TaskType.valueOf(lineTask[1]);
        String name = lineTask[2];
        String description = lineTask[3];
        Status status = Status.valueOf(lineTask[4]);
        Integer duration = lineTask.length > 6 ? Integer.valueOf(lineTask[5]) : null;
        LocalDateTime startTime = lineTask.length > 6 ? LocalDateTime.parse(lineTask[6], formatter) : null;

        switch (type) {
            case TASK:
                if (duration == null) {
                    return new Task(id, type, name, description, status);
                }
                return new Task(id, type, name, description, status, duration,
                        startTime);
            case EPIC:
                LocalDateTime endTime = LocalDateTime.parse(lineTask[7].trim());
                if (duration == null) {
                    return new Epic(id, type, name, description, status);
                }
                return new Epic(id, type, name, description, status, duration,
                        startTime, endTime);
            case SUBTASK:
                String epicId = lineTask[5].trim();
                if (duration == null) {
                    return new Subtask(id, type, name, description, status, Integer.valueOf(epicId));
                }
                return new Subtask(id, type, name, description, status, duration, startTime, Integer.valueOf(epicId));
            default:
                return null;
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManagers = new FileBackedTaskManager(file.toPath());
        int maxId = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();

            Task task = fromString(line);
            TaskType type = task.getType();
            Subtask subtask;
            Epic epic;

                switch (type) {
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
                        taskManagers.epics.get(epicId).addSubtaskId(subtask.getId());
                        if (subtask.getId() > maxId) {
                            maxId = subtask.getId();
                        }
                        break;
                }
            taskManagers.generateId = maxId;
            String lineHistory = bufferedReader.readLine();
            List<Integer> idTask = new ArrayList<>(historyFromString(lineHistory));
            for (Integer id : idTask) {
                if (taskManagers.epics.containsKey(id)) {
                    taskManagers.historyManager.add(taskManagers.epics.get(id));
                } else if (taskManagers.subtasks.containsKey(id)) {
                    taskManagers.historyManager.add(taskManagers.subtasks.get(id));
                } else {
                    taskManagers.historyManager.add(taskManagers.tasks.get(id));
                }
            }
        return taskManagers;
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось считать данные из файла.");
        }
    }
}

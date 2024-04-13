package Entity;

import Manager.TaskType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Epic extends Task {
    protected ArrayList<Integer> subtaskId =  new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic (Integer id, TaskType type, String name, String description, Status status,
                Integer duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(id, type, name, description, status, duration, startTime);
        this.endTime = endTime;
    }

    public Epic (Integer id, TaskType type, String name, String description, Status status) {
        super(id, type, name, description, status);
    }

    public Epic (String name, String description, Status status, Integer duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
    }

    @Override
    public TaskType getType()  {
        return TaskType.EPIC;
    }

    public ArrayList<Integer> getSubtaskId()  {
        return subtaskId;
    }

    public void addSubtaskId(Integer id)  {
        if (!getId().equals(id))
            subtaskId.add(id);
    }
    public void setSubtaskId(ArrayList<Integer> subtaskId)  {
        if (subtaskId == null) {
            subtaskId = new ArrayList<>();
        }
        this.subtaskId = subtaskId;
    }
    public void cleanSubtaskId()  {
        subtaskId.clear();
    }
    public void removeSubtaskId(Integer id)  {
        subtaskId.remove(id);
    }

    public LocalDateTime getEndTime()  {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime)  {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o)  {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskId, epic.subtaskId) && Objects.equals(endTime, epic.endTime);
    }
    @Override
    public int hashCode()  {
        return Objects.hash(super.hashCode(), subtaskId, endTime);
    }
    @Override
    public String toString()  {
        String result = "Models.Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status;
        if (subtaskId == null) {
            result = result + ", subtaskId=null";
        } else {
            result = result + ", subtaskId=" + subtaskId + '\'';
        }
        if (startTime != null)  {
            result = result + ", duration='" + duration.toMinutes() + ", startTime='" + startTime.format(formatter) +
            ", endTime='" + endTime.format(formatter);
        }

        return result + "}";
    }
}
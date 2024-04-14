package Entity;

import Manager.TaskType;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    protected int epicId;

    public Subtask (String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask (Integer id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public Subtask (Integer id, TaskType type, String name, String description, Status status, int epicId) {
        super(id, type, name, description, status);
        this.epicId = epicId;
    }

    public Subtask (Integer id, TaskType type, String name, String description, Status status,
                   Integer duration, LocalDateTime startTime, int epicId) {
        super(id, type, name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask (String name, String description, Status status,
                   Integer duration, LocalDateTime startTime, int epicId) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask (Integer id, String name, String description, Status status,
                    Integer duration, LocalDateTime startTime, int epicId) {
        super(id, name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    @Override
    public TaskType getType()  {
        return TaskType.SUBTASK;
    }

    public Integer getEpicId()  {
        return epicId;
    }

    public void setEpicId(Integer epicId)  {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o)  {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask)  o;
        return Objects.equals(epicId, subtask.epicId);
    }

    @Override
    public int hashCode()  {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString()  {
        String result = "Models.Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status;
        if (startTime != null)  {
            result = result + ", duration='" + duration.toMinutes() + ", startTime=" + startTime.format(formatter);
        }
        return result + '}';
    }
}
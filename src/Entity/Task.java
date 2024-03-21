package Entity;

import Manager.TaskType;

import java.util.Objects;
public class Task {
    protected TaskType type;
    protected String name;
    protected String description;
    protected Integer id;
    protected Status status;

    public Task(Integer id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(Integer id, TaskType type, String name, String description, Status status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    public Integer getEpicId() {
        return 0;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && id.equals(task.id)
                && status == task.status;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }
    @Override
    public String toString() {
        return "Models.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}


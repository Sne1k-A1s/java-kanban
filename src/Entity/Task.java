package Entity;

import Manager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task implements Comparable<Task> {
    protected TaskType type;
    protected String name;
    protected String description;
    protected Integer id;
    protected Status status;
    protected LocalDateTime  startTime;
    protected Duration  duration;
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm");

    public Task (String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task (Integer id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task (Integer id, String name, String description, Status status, Integer duration, LocalDateTime startTime)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
    }

    public Task (String name, String description, Status status, Integer duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
    }

    public Task(Integer id, TaskType type, String name, String description, Status status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.status = status;
    }
    public Task (Integer id, TaskType type, String name, String description, Status status, Integer duration,
                LocalDateTime startTime) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
    }

    @Override
    public int compareTo(Task o) {
        LocalDateTime t1 = this.getStartTime();
        LocalDateTime t2 = o.getStartTime();
        if (t1 != null && t2 != null) return t1.compareTo(t2);
        else if (t1 != null) return +1;
        else if (t2 != null) return -1;
        else return this.id - o.id;
    }

    public TaskType getType()  {
        return TaskType.TASK;
    }

    public Integer getId()  {
        return id;
    }

    public void setId(Integer id)  {
        this.id = id;
    }

    public String getName()  {
        return name;
    }

    public void setName(String name)  {
        this.name = name;
    }

    public String getDescription()  {
        return description;
    }

    public void setDescription(String description)  {
        this.description = description;
    }

    public Status getStatus()  {
        return status;
    }

    public void setStatus(Status status)  {
        this.status = status;
    }

    public Duration getDuration()  {
        return duration;
    }

    public void setDuration(Duration duration)  {
        this.duration = duration;
    }

    public LocalDateTime getStartTime()  {
        try {
            return startTime;
        } catch (NullPointerException e) {
            return LocalDateTime.parse("Невозможно получить время начало задачи");
        }
    }

    public void setStartTime(LocalDateTime startTime)  {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime()  {
        return startTime != null && duration != null ? startTime.plus(duration) : null;
    }

    public String getStringStartTime()  {
        return startTime != null ? startTime.format(formatter) : null;
    }

    public void setStringStartTime(String startTime)  {
        this.startTime = LocalDateTime.parse(startTime, formatter);
    }

    public String getStringEndTime()  {
        try {
            LocalDateTime end = startTime.plus(duration);
            return end.format(formatter);
        } catch (NullPointerException e) {
            return "Невозможно получить конечное время, не указано начало, или продолжительность";
        }
    }

    @Override
    public boolean equals(Object o)  {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                id.equals(task.id) &&
                status == task.status  &&
                Objects.equals(startTime, task.startTime) &&
                Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode()  {
        return Objects.hash(name, description, id, status, duration, startTime);
    }

    @Override
    public String toString()  {
        String result = "Models.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=ц" + id +
                ", status=" + status;
        if(startTime != null)  {
            result = result + ", duration='" + duration.toMinutes() + '\'' +
                    ", startTime='" + startTime.format(formatter);
        }
        return  result + '}';
    }
}
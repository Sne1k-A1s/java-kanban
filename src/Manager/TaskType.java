package Manager;

public enum TaskType {
    TASK("TASK"), SUBTASK("SUBTASK"), EPIC("EPIC");

    private final String taskType;

    TaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskType() {
        return taskType;
    }
}

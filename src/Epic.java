import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }
    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }


    public void addSubtaskId(Integer id) {
        subtaskId.add(id);
    }
    public void setSubtaskId(ArrayList<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }
    public void cleanSubtaskId() {
        subtaskId.clear();
    }
    public void removeSubtaskId(Integer id) {
        subtaskId.remove(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskId, epic.subtaskId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskId);
    }
    @Override
    public String toString() {
        String result = "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status;
        if (subtaskId == null) {
            result = result + "subtaskId=null";
        } else {
            result = result + "subtaskId=" + subtaskId + '\'';
        }
        return result + "}";
    }
}


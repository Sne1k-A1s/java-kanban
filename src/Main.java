public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("таск1", "задача таска1", Status.NEW);
        taskManager.addNewTask(task1);
        Task updatetask1 = new Task(1,"обнавленный таск1", "задача таска1", Status.IN_PROGRESS);
        System.out.println(taskManager.getTasks());
        taskManager.updateTask(updatetask1);
        System.out.println(taskManager.getTasks());

        Epic epic1 = new Epic("epic1", "задача эпика1", Status.NEW);
        final int epicId1 = taskManager.addNewEpic(epic1);
        System.out.println(taskManager.getEpics());

        Subtask subtask1 = new Subtask("субстаск1 эпика1", "задача субтаска1", Status.NEW, epicId1);
        Subtask subtask2 = new Subtask("субстаск2 эпика1", "задача субтаска2", Status.NEW, epicId1);
        Subtask subtask3 = new Subtask("субстаск3 эпика1", "задача субтаска3", Status.NEW, epicId1);

        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);
        System.out.println(taskManager.getSubtasks());
        Subtask updatesubtask2 = new Subtask(4,"обновленный субстаск2 эпика1", "задача субтаска2",
                Status.DONE, epicId1);
        Subtask updatesubtask1 = new Subtask(3,"обновленный субстаск1 эпика1", "задача субтаска1",
                Status.DONE, epicId1);
        taskManager.updateSubtask(updatesubtask2);
        taskManager.updateSubtask(updatesubtask1);
        System.out.println(taskManager.getSubtasks());

        Epic updateEpic1 = new Epic(epicId1,"обновленный epic1", "задача эпика1", Status.NEW);
        taskManager.updateEpic(updateEpic1);
        taskManager.deleteSubtask(5);
        System.out.println(taskManager.getEpicSubtask(2));
    }
}
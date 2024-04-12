package Test;

import Manager.FileBackedTaskManager;
import exceptions.ManagerSaveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    private Path path = Path.of(("src/sources/FileBackedTaskManager.csv"));
    private File file = new File(String.valueOf(path));

    @Override
    public FileBackedTaskManager createTaskManager() {
        return new FileBackedTaskManager(path);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void shouldSaveOfEmptyThrowException() {
        final ManagerSaveException exception = assertThrows(
                ManagerSaveException.class,
                () -> {
                    FileBackedTaskManager.loadFromFile(file);
                });

        assertEquals("Не удалось считать данные из файла.", exception.getMessage());
    }

    @Test
    public void shouldSaveEmptyHistoryThrowException() {
        final ManagerSaveException exception = assertThrows(
                ManagerSaveException.class,
                () -> {
                    FileBackedTaskManager.loadFromFile(file).getHistory();
                });

        assertEquals("Не удалось считать данные из файла.", exception.getMessage());
    }

}
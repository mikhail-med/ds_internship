package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;


    @Test
    void getAll() {
        doReturn(List.of(new Task())).when(taskRepository).findAll();

        List<Task> tasks = taskServiceImpl.getAll();

        assertEquals(1, tasks.size());
    }

    @Test
    void getById() {
        Task task = new Task();
        task.setId(1);
        doReturn(Optional.of(task)).when(taskRepository).findById(1);

        Task taskReturned = taskServiceImpl.getById(1);

        assertEquals(1, taskReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(taskRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> taskServiceImpl.getById(1));
    }

    @Test
    void save() {
        Task toSave = mock(Task.class);
        Task taskToReturn = new Task();
        taskToReturn.setId(1);
        doReturn(taskToReturn).when(taskRepository).save(toSave);

        Task taskReturned = taskServiceImpl.save(toSave);

        assertEquals(1, taskReturned.getId());
    }

    @Test
    void update() {
        Task toUpdate = mock(Task.class);
        Task taskToReturn = new Task();
        taskToReturn.setId(1);
        doReturn(taskToReturn).when(taskRepository).save(toUpdate);
        doReturn(true).when(taskRepository).existsById(1);

        Task taskUpdated = taskServiceImpl.update(1, toUpdate);

        assertEquals(1, taskUpdated.getId());
    }

    @Test
    void update_TaskNotExisting_ThrowsException() {
        Task toUpdate = mock(Task.class);
        doReturn(false).when(taskRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> taskServiceImpl.update(1, toUpdate));
        Mockito.verify(taskRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        doReturn(true).when(taskRepository).existsById(1);
        taskServiceImpl.delete(1);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1);
    }
}
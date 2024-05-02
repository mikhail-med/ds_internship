package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;


    @Test
    void getAll() {
        doReturn(List.of(new Task())).when(taskRepository).findAll();

        assertEquals(1, taskService.getAll().size());
        Mockito.verify(taskRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdWithExistingValue() {
        Task task = new Task();
        task.setId(1);
        doReturn(Optional.of(task)).when(taskRepository).findById(1);

        assertEquals(1, taskService.getById(1).getId());
        Mockito.verify(taskRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getByIdWithNotExistingValue() {
        doReturn(Optional.empty()).when(taskRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> taskService.getById(1));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        Task toSave = mock(Task.class);
        Task taskToReturn = new Task();
        taskToReturn.setId(1);
        doReturn(taskToReturn).when(taskRepository).save(toSave);

        assertEquals(1, taskService.save(toSave).getId());
        Mockito.verify(taskRepository, Mockito.times(1)).save(toSave);
    }

    @Test
    void updateExisting() {
        Task toUpdate = mock(Task.class);
        Task taskToReturn = new Task();
        taskToReturn.setId(1);
        doReturn(taskToReturn).when(taskRepository).save(toUpdate);
        doReturn(true).when(taskRepository).existsById(1);

        assertEquals(1, taskService.update(1, toUpdate).getId());
        Mockito.verify(taskRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(taskRepository, Mockito.times(1)).save(toUpdate);
    }

    @Test
    void updateNotExisting() {
        Task toUpdate = mock(Task.class);
        Task taskToReturn = new Task();
        taskToReturn.setId(1);
        doReturn(taskToReturn).when(taskRepository).save(toUpdate);
        doReturn(false).when(taskRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> taskService.update(1, toUpdate));
        Mockito.verify(taskRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(taskRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        taskService.delete(1);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1);
    }
}
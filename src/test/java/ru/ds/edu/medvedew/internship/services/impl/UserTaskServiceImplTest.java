package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.UserTask;
import ru.ds.edu.medvedew.internship.repositories.UserTaskRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserTaskServiceImplTest {
    @Mock
    private UserTaskRepository userTaskRepository;

    @InjectMocks
    private UserTaskServiceImpl userTaskServiceImpl;

    @Test
    void getAll() {
        doReturn(List.of(new UserTask())).when(userTaskRepository).findAll();

        List<UserTask> userTasks = userTaskServiceImpl.getAll();

        assertEquals(1, userTasks.size());
    }

    @Test
    void getByIdWithExistingValue() {
        UserTask userTask = new UserTask();
        userTask.setId(1);
        doReturn(Optional.of(userTask)).when(userTaskRepository).findById(1);

        UserTask userTaskReturned = userTaskServiceImpl.getById(1);

        assertEquals(1, userTaskReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(userTaskRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> userTaskServiceImpl.getById(1));
    }

    @Test
    void save() {
        UserTask toSave = mock(UserTask.class);
        UserTask userTaskToReturn = new UserTask();
        userTaskToReturn.setId(1);
        doReturn(userTaskToReturn).when(userTaskRepository).save(toSave);

        UserTask userTask = userTaskServiceImpl.save(toSave);

        assertEquals(1, userTask.getId());
    }

    @Test
    void updateExisting() {
        UserTask toUpdate = mock(UserTask.class);
        UserTask userTaskToReturn = new UserTask();
        userTaskToReturn.setId(1);
        doReturn(userTaskToReturn).when(userTaskRepository).save(toUpdate);
        doReturn(true).when(userTaskRepository).existsById(1);

        UserTask userTask = userTaskServiceImpl.update(1, toUpdate);

        assertEquals(1, userTask.getId());
    }

    @Test
    void updateNotExisting() {
        UserTask toUpdate = mock(UserTask.class);
        doReturn(false).when(userTaskRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> userTaskServiceImpl.update(1, toUpdate));
        Mockito.verify(userTaskRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        doReturn(true).when(userTaskRepository).existsById(1);
        userTaskServiceImpl.delete(1);
        Mockito.verify(userTaskRepository, Mockito.times(1)).deleteById(1);
    }
}
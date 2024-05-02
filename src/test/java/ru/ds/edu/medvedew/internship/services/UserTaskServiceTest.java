package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.UserTask;
import ru.ds.edu.medvedew.internship.repositories.UserTaskRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class UserTaskServiceTest {
    @Autowired
    private UserTaskService userTaskService;

    @MockBean
    private UserTaskRepository userTaskRepository;


    @Test
    void getAll() {
        doReturn(List.of(new UserTask())).when(userTaskRepository).findAll();

        assertEquals(1, userTaskService.getAll().size());
        Mockito.verify(userTaskRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdWithExistingValue() {
        UserTask userTask = new UserTask();
        userTask.setId(1);
        doReturn(Optional.of(userTask)).when(userTaskRepository).findById(1);

        assertEquals(1, userTaskService.getById(1).getId());
        Mockito.verify(userTaskRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getByIdWithNotExistingValue() {
        doReturn(Optional.empty()).when(userTaskRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> userTaskService.getById(1));
        Mockito.verify(userTaskRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        UserTask toSave = mock(UserTask.class);
        UserTask userTaskToReturn = new UserTask();
        userTaskToReturn.setId(1);
        doReturn(userTaskToReturn).when(userTaskRepository).save(toSave);

        assertEquals(1, userTaskService.save(toSave).getId());
        Mockito.verify(userTaskRepository, Mockito.times(1)).save(toSave);
    }

    @Test
    void updateExisting() {
        UserTask toUpdate = mock(UserTask.class);
        UserTask userTaskToReturn = new UserTask();
        userTaskToReturn.setId(1);
        doReturn(userTaskToReturn).when(userTaskRepository).save(toUpdate);
        doReturn(true).when(userTaskRepository).existsById(1);

        assertEquals(1, userTaskService.update(1, toUpdate).getId());
        Mockito.verify(userTaskRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(userTaskRepository, Mockito.times(1)).save(toUpdate);
    }

    @Test
    void updateNotExisting() {
        UserTask toUpdate = mock(UserTask.class);
        UserTask userTaskToReturn = new UserTask();
        userTaskToReturn.setId(1);
        doReturn(userTaskToReturn).when(userTaskRepository).save(toUpdate);
        doReturn(false).when(userTaskRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> userTaskService.update(1, toUpdate));
        Mockito.verify(userTaskRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(userTaskRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        userTaskService.delete(1);
        Mockito.verify(userTaskRepository, Mockito.times(1)).deleteById(1);
    }
}
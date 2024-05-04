package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.repositories.UserInternshipRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class UserInternshipServiceTest {
    @Autowired
    private UserInternshipService userInternshipService;

    @MockBean
    private UserInternshipRepository userInternshipRepository;


    @Test
    void getAll() {
        doReturn(List.of(new UserInternship())).when(userInternshipRepository).findAll();

        assertEquals(1, userInternshipService.getAll().size());
        Mockito.verify(userInternshipRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdWithExistingValue() {
        UserInternship userInternship = new UserInternship();
        userInternship.setId(1);
        doReturn(Optional.of(userInternship)).when(userInternshipRepository).findById(1);

        assertEquals(1, userInternshipService.getById(1).getId());
        Mockito.verify(userInternshipRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getByIdWithNotExistingValue() {
        doReturn(Optional.empty()).when(userInternshipRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> userInternshipService.getById(1));
        Mockito.verify(userInternshipRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        UserInternship toSave = mock(UserInternship.class);
        UserInternship userInternshipToReturn = new UserInternship();
        userInternshipToReturn.setId(1);
        doReturn(userInternshipToReturn).when(userInternshipRepository).save(toSave);

        assertEquals(1, userInternshipService.save(toSave).getId());
        Mockito.verify(userInternshipRepository, Mockito.times(1)).save(toSave);
    }

    @Test
    void updateExisting() {
        UserInternship toUpdate = mock(UserInternship.class);
        UserInternship userInternshipToReturn = new UserInternship();
        userInternshipToReturn.setId(1);
        doReturn(userInternshipToReturn).when(userInternshipRepository).save(toUpdate);
        doReturn(true).when(userInternshipRepository).existsById(1);

        assertEquals(1, userInternshipService.update(1, toUpdate).getId());
        Mockito.verify(userInternshipRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(userInternshipRepository, Mockito.times(1)).save(toUpdate);
    }

    @Test
    void updateNotExisting() {
        UserInternship toUpdate = mock(UserInternship.class);
        UserInternship userInternshipToReturn = new UserInternship();
        userInternshipToReturn.setId(1);
        doReturn(userInternshipToReturn).when(userInternshipRepository).save(toUpdate);
        doReturn(false).when(userInternshipRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> userInternshipService.update(1, toUpdate));
        Mockito.verify(userInternshipRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(userInternshipRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        userInternshipService.delete(1);
        Mockito.verify(userInternshipRepository, Mockito.times(1)).deleteById(1);
    }
}

package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.repositories.UserInternshipRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserInternshipServiceImplTest {
    @Mock
    private UserInternshipRepository userInternshipRepository;

    @InjectMocks
    private UserInternshipServiceImpl userInternshipImplService;


    @Test
    void getAll() {
        doReturn(List.of(new UserInternship())).when(userInternshipRepository).findAll();

        List<UserInternship> userInternships = userInternshipImplService.getAll();

        assertEquals(1, userInternships.size());
    }

    @Test
    void getById() {
        UserInternship userInternship = new UserInternship();
        userInternship.setId(1);
        doReturn(Optional.of(userInternship)).when(userInternshipRepository).findById(1);

        UserInternship userInternshipReturned = userInternshipImplService.getById(1);

        assertEquals(1, userInternshipReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(userInternshipRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> userInternshipImplService.getById(1));
    }

    @Test
    void save() {
        UserInternship toSave = mock(UserInternship.class);
        UserInternship userInternshipToReturn = new UserInternship();
        userInternshipToReturn.setId(1);
        doReturn(userInternshipToReturn).when(userInternshipRepository).save(toSave);

        UserInternship userInternship = userInternshipImplService.save(toSave);

        assertEquals(1, userInternship.getId());
    }

    @Test
    void update() {
        UserInternship toUpdate = mock(UserInternship.class);
        UserInternship userInternshipToReturn = new UserInternship();
        userInternshipToReturn.setId(1);
        doReturn(userInternshipToReturn).when(userInternshipRepository).save(toUpdate);
        doReturn(true).when(userInternshipRepository).existsById(1);

        UserInternship userInternshipReturned = userInternshipImplService.update(1, toUpdate);

        assertEquals(1, userInternshipReturned.getId());
    }

    @Test
    void updateNotExisting() {
        UserInternship toUpdate = mock(UserInternship.class);
        doReturn(false).when(userInternshipRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> userInternshipImplService.update(1, toUpdate));
        Mockito.verify(userInternshipRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        doReturn(true).when(userInternshipRepository).existsById(1);
        userInternshipImplService.delete(1);
        Mockito.verify(userInternshipRepository, Mockito.times(1)).deleteById(1);
    }
}

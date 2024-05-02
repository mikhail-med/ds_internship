package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.repositories.InternshipRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class InternshipServiceTest {
    @Autowired
    private InternshipService internshipService;

    @MockBean
    private InternshipRepository internshipRepository;


    @Test
    void getAll() {
        doReturn(List.of(new Internship())).when(internshipRepository).findAll();

        assertEquals(1, internshipService.getAll().size());
        Mockito.verify(internshipRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdWithExistingValue() {
        Internship internship = new Internship();
        internship.setId(1);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        assertEquals(1, internshipService.getById(1).getId());
        Mockito.verify(internshipRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getByIdWithNotExistingValue() {
        doReturn(Optional.empty()).when(internshipRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> internshipService.getById(1));
        Mockito.verify(internshipRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        Internship toSave = mock(Internship.class);
        Internship internshipToReturn = new Internship();
        internshipToReturn.setId(1);
        doReturn(internshipToReturn).when(internshipRepository).save(toSave);

        assertEquals(1, internshipService.save(toSave).getId());
        Mockito.verify(internshipRepository, Mockito.times(1)).save(toSave);
    }

    @Test
    void updateExisting() {
        Internship toUpdate = mock(Internship.class);
        Internship internshipToReturn = new Internship();
        internshipToReturn.setId(1);
        doReturn(internshipToReturn).when(internshipRepository).save(toUpdate);
        doReturn(true).when(internshipRepository).existsById(1);

        assertEquals(1, internshipService.update(1, toUpdate).getId());
        Mockito.verify(internshipRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(internshipRepository, Mockito.times(1)).save(toUpdate);
    }

    @Test
    void updateNotExisting() {
        Internship toUpdate = mock(Internship.class);
        Internship internshipToReturn = new Internship();
        internshipToReturn.setId(1);
        doReturn(internshipToReturn).when(internshipRepository).save(toUpdate);
        doReturn(false).when(internshipRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> internshipService.update(1, toUpdate));
        Mockito.verify(internshipRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(internshipRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        internshipService.delete(1);
        Mockito.verify(internshipRepository, Mockito.times(1)).deleteById(1);
    }
}
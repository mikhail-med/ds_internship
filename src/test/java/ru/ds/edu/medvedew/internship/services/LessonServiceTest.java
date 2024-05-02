package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class LessonServiceTest {
    @Autowired
    private LessonService lessonService;

    @MockBean
    private LessonRepository lessonRepository;


    @Test
    void getAll() {
        doReturn(List.of(new Lesson())).when(lessonRepository).findAll();

        assertEquals(1, lessonService.getAll().size());
        Mockito.verify(lessonRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdWithExistingValue() {
        Lesson lesson = new Lesson();
        lesson.setId(1);
        doReturn(Optional.of(lesson)).when(lessonRepository).findById(1);

        assertEquals(1, lessonService.getById(1).getId());
        Mockito.verify(lessonRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getByIdWithNotExistingValue() {
        doReturn(Optional.empty()).when(lessonRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> lessonService.getById(1));
        Mockito.verify(lessonRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        Lesson toSave = mock(Lesson.class);
        Lesson lessonToReturn = new Lesson();
        lessonToReturn.setId(1);
        doReturn(lessonToReturn).when(lessonRepository).save(toSave);

        assertEquals(1, lessonService.save(toSave).getId());
        Mockito.verify(lessonRepository, Mockito.times(1)).save(toSave);
    }

    @Test
    void updateExisting() {
        Lesson toUpdate = mock(Lesson.class);
        Lesson lessonToReturn = new Lesson();
        lessonToReturn.setId(1);
        doReturn(lessonToReturn).when(lessonRepository).save(toUpdate);
        doReturn(true).when(lessonRepository).existsById(1);

        assertEquals(1, lessonService.update(1, toUpdate).getId());
        Mockito.verify(lessonRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(lessonRepository, Mockito.times(1)).save(toUpdate);
    }

    @Test
    void updateNotExisting() {
        Lesson toUpdate = mock(Lesson.class);
        Lesson lessonToReturn = new Lesson();
        lessonToReturn.setId(1);
        doReturn(lessonToReturn).when(lessonRepository).save(toUpdate);
        doReturn(false).when(lessonRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> lessonService.update(1, toUpdate));
        Mockito.verify(lessonRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(lessonRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        lessonService.delete(1);
        Mockito.verify(lessonRepository, Mockito.times(1)).deleteById(1);
    }
}

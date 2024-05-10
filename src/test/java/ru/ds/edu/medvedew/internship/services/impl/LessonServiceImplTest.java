package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class LessonServiceImplTest {
    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonServiceImpl lessonServiceImpl;

    @Test
    void getAll() {
        doReturn(List.of(new Lesson())).when(lessonRepository).findAll();

        List<Lesson> lessonsReturned = lessonServiceImpl.getAll();
        assertEquals(1, lessonsReturned.size());
    }

    @Test
    void getById() {
        Lesson lesson = new Lesson();
        lesson.setId(1);
        doReturn(Optional.of(lesson)).when(lessonRepository).findById(1);

        Lesson lessonReturned = lessonServiceImpl.getById(1);

        assertEquals(1, lessonReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(lessonRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> lessonServiceImpl.getById(1));
    }

    @Test
    void save() {
        Lesson toSave = mock(Lesson.class);
        Lesson lessonToReturn = new Lesson();
        lessonToReturn.setId(1);
        doReturn(lessonToReturn).when(lessonRepository).save(toSave);

        Lesson lessonSaved = lessonServiceImpl.save(toSave);

        assertEquals(1, lessonSaved.getId());
    }

    @Test
    void update() {
        Lesson toUpdate = mock(Lesson.class);
        Lesson lessonToReturn = new Lesson();
        lessonToReturn.setId(1);
        doReturn(lessonToReturn).when(lessonRepository).save(toUpdate);
        doReturn(true).when(lessonRepository).existsById(1);

        Lesson lessonUpdated = lessonServiceImpl.update(1, toUpdate);

        assertEquals(1, lessonUpdated.getId());
    }

    @Test
    void update_LessonNotExisting_ThrowsException() {
        doReturn(false).when(lessonRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> lessonServiceImpl.update(1, new Lesson()));
    }

    @Test
    void delete() {
        doReturn(true).when(lessonRepository).existsById(1);
        lessonServiceImpl.delete(1);
        Mockito.verify(lessonRepository, Mockito.times(1)).deleteById(1);
    }
}

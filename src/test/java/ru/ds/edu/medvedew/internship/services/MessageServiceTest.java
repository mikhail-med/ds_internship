package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.repositories.LessonRepository;
import ru.ds.edu.medvedew.internship.repositories.MessageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;


    @Test
    void getAll() {
        doReturn(List.of(new Message())).when(messageRepository).findAll();

        assertEquals(1, messageService.getAll().size());
        Mockito.verify(messageRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdWithExistingValue() {
        Message message = new Message();
        message.setId(1);
        doReturn(Optional.of(message)).when(messageRepository).findById(1);

        assertEquals(1, messageService.getById(1).getId());
        Mockito.verify(messageRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getByIdWithNotExistingValue() {
        doReturn(Optional.empty()).when(messageRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> messageService.getById(1));
        Mockito.verify(messageRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        Message toSave = mock(Message.class);
        Message messageToReturn = new Message();
        messageToReturn.setId(1);
        doReturn(messageToReturn).when(messageRepository).save(toSave);

        assertEquals(1, messageService.save(toSave).getId());
        Mockito.verify(messageRepository, Mockito.times(1)).save(toSave);
    }

    @Test
    void updateExisting() {
        Message toUpdate = mock(Message.class);
        Message messageToReturn = new Message();
        messageToReturn.setId(1);
        doReturn(messageToReturn).when(messageRepository).save(toUpdate);
        doReturn(true).when(messageRepository).existsById(1);

        assertEquals(1, messageService.update(1, toUpdate).getId());
        Mockito.verify(messageRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(messageRepository, Mockito.times(1)).save(toUpdate);
    }

    @Test
    void updateNotExisting() {
        Message toUpdate = mock(Message.class);
        Message messageToReturn = new Message();
        messageToReturn.setId(1);
        doReturn(messageToReturn).when(messageRepository).save(toUpdate);
        doReturn(false).when(messageRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> messageService.update(1, toUpdate));
        Mockito.verify(messageRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(messageRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        messageService.delete(1);
        Mockito.verify(messageRepository, Mockito.times(1)).deleteById(1);
    }
}
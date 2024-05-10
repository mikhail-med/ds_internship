package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.repositories.MessageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImpl messageServiceImpl;

    @Test
    void getAll() {
        doReturn(List.of(new Message())).when(messageRepository).findAll();

        List<Message> messagesReturned = messageServiceImpl.getAll();

        assertEquals(1, messagesReturned.size());
    }

    @Test
    void getById() {
        Message message = new Message();
        message.setId(1);
        doReturn(Optional.of(message)).when(messageRepository).findById(1);

        Message messageReturned = messageServiceImpl.getById(1);

        assertEquals(1, messageReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(messageRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> messageServiceImpl.getById(1));
    }

    @Test
    void save() {
        Message toSave = mock(Message.class);
        Message messageToReturn = new Message();
        messageToReturn.setId(1);
        doReturn(messageToReturn).when(messageRepository).save(toSave);

        Message messageSaved = messageServiceImpl.save(toSave);

        assertEquals(1, messageSaved.getId());
    }

    @Test
    void update() {
        Message toUpdate = mock(Message.class);
        Message messageToReturn = new Message();
        messageToReturn.setId(1);
        doReturn(messageToReturn).when(messageRepository).save(toUpdate);
        doReturn(true).when(messageRepository).existsById(1);

        Message messageUpdated = messageServiceImpl.update(1, toUpdate);

        assertEquals(1, messageUpdated.getId());
    }

    @Test
    void update_MessageNotExisting() {
        Message toUpdate = mock(Message.class);
        doReturn(false).when(messageRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> messageServiceImpl.update(1, toUpdate));
    }

    @Test
    void delete() {
        doReturn(true).when(messageRepository).existsById(1);
        messageServiceImpl.delete(1);
        Mockito.verify(messageRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void getAllMessagesBetweenUsers() {
        doReturn(List.of(new Message())).when(messageRepository).findAllBetweenUsers(1, 2);

        List<Message> messagesReturned = messageServiceImpl.getAllMessagesBetweenUsers(1, 2);

        assertEquals(1, messagesReturned.size());
    }

    @Test
    public void sendMessageFromUserToUserWithText() {
        Message toSave = new Message();
        doReturn(toSave).when(messageRepository).save(any(Message.class));

        Message sent = messageServiceImpl.sendMessageFromUserToUserWithText(1, 2, "text");

        Mockito.verify(messageRepository).save(argThat(message -> {
            return message.getSender().getId() == 1 && message.getConsumer().getId() == 2
                    && message.getMessage().equals("text");
        }));
    }

}
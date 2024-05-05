package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Test
    void getAll() {
        doReturn(List.of(new User())).when(userRepository).findAll();

        assertEquals(1, userService.getAll().size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdWithExistingValue() {
        User user = new User();
        user.setId(1);
        doReturn(Optional.of(user)).when(userRepository).findById(1);

        assertEquals(1, userService.getById(1).getId());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getByIdWithNotExistingValue() {
        doReturn(Optional.empty()).when(userRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(1));
        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        User toSave = mock(User.class);
        doReturn("password").when(toSave).getPassword();
        User userToReturn = new User();
        userToReturn.setId(1);
        doReturn(userToReturn).when(userRepository).save(toSave);

        assertEquals(1, userService.save(toSave).getId());
        Mockito.verify(userRepository, Mockito.times(1)).save(toSave);
    }

    @Test
    void updateExisting() {
        User toUpdate = mock(User.class);
        User userToReturn = new User();
        userToReturn.setId(1);
        doReturn(userToReturn).when(userRepository).save(toUpdate);
        doReturn(true).when(userRepository).existsById(1);

        assertEquals(1, userService.update(1, toUpdate).getId());
        Mockito.verify(userRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(userRepository, Mockito.times(1)).save(toUpdate);
    }

    @Test
    void updateNotExisting() {
        User toUpdate = mock(User.class);
        User userToReturn = new User();
        userToReturn.setId(1);
        doReturn(userToReturn).when(userRepository).save(toUpdate);
        doReturn(false).when(userRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> userService.update(1, toUpdate));
        Mockito.verify(userRepository, Mockito.times(1)).existsById(1);
        Mockito.verify(userRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        userService.delete(1);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1);
    }


    @Test
    public void getAllMessagesForUser() {
        User user = new User();
        user.setId(1);
        Message m1 = new Message();
        m1.setId(1);
        Message m2 = new Message();
        m2.setId(2);
        user.setSentMessages(Set.of(m1));
        user.setReceivedMessages(Set.of(m2));
        doReturn(Optional.of(user)).when(userRepository).findById(1);
        assertEquals(2, userService.getAllMessagesForUser(1).size());
    }

    @Test
    public void getAllMessagesForNotExistingUser() {
        doReturn(Optional.empty()).when(userRepository).findById(1);
        assertThrows(ResourceNotFoundException.class, () ->
                userService.getAllMessagesForUser(1));
    }

    @Test
    public void getAllMessagesSentByUser() {
        User user = new User();
        user.setId(1);
        Message m1 = new Message();
        m1.setId(1);
        Message m2 = new Message();
        m2.setId(2);
        user.setSentMessages(Set.of(m1, m2));
        doReturn(Optional.of(user)).when(userRepository).findById(1);
        assertEquals(2, userService.getAllMessagesSentByUser(1).size());
    }

    @Test
    public void getAllMessageReceivedByUser() {
        User user = new User();
        user.setId(1);
        Message m1 = new Message();
        m1.setId(1);
        Message m2 = new Message();
        m2.setId(2);
        user.setReceivedMessages(Set.of(m2, m1));
        doReturn(Optional.of(user)).when(userRepository).findById(1);
        assertEquals(2, userService.getAllMessageReceivedByUser(1).size());
    }
}
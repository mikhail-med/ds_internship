package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.repositories.UserRepository;
import ru.ds.edu.medvedew.internship.services.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void getAll() {
        doReturn(List.of(new User())).when(userRepository).findAll();

        List<User> users = userServiceImpl.getAll();

        assertEquals(1, users.size());
    }

    @Test
    void getById() {
        User user = new User();
        user.setId(1);
        doReturn(Optional.of(user)).when(userRepository).findById(1);

        User userReturned = userServiceImpl.getById(1);

        assertEquals(1, userReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(userRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getById(1));
    }

    @Test
    void save() {
        doReturn("encodedPassword").when(passwordEncoder).encode(any());
        User toSave = mock(User.class);
        doReturn("password").when(toSave).getPassword();
        User userToReturn = new User();
        userToReturn.setId(1);
        doReturn(userToReturn).when(userRepository).save(toSave);

        User saved = userServiceImpl.save(toSave);

        assertEquals(1, saved.getId());
        verify(toSave).setPassword("encodedPassword");
    }

    @Test
    void update() {
        doReturn("encodedPassword").when(passwordEncoder).encode(any());
        User toUpdate = mock(User.class);
        User userToReturn = new User();
        userToReturn.setId(1);
        doReturn(userToReturn).when(userRepository).save(toUpdate);
        doReturn(true).when(userRepository).existsById(1);

        User userUpdated = userServiceImpl.update(1, toUpdate);

        assertEquals(1, userUpdated.getId());
        verify(toUpdate).setPassword("encodedPassword");
    }

    @Test
    void update_UserNotExisting_ThrowsException() {
        User toUpdate = mock(User.class);
        User userToReturn = new User();
        userToReturn.setId(1);
        doReturn(false).when(userRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.update(1, toUpdate));
        Mockito.verify(userRepository, Mockito.times(1)).existsById(1);
    }

    @Test
    void delete() {
        doReturn(true).when(userRepository).existsById(1);
        userServiceImpl.delete(1);
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

        List<Message> messages = userServiceImpl.getAllMessagesForUser(1);

        assertEquals(2, messages.size());
    }

    @Test
    public void getAllMessages_UserNotExisting_ThrowsException() {
        doReturn(Optional.empty()).when(userRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () ->
                userServiceImpl.getAllMessagesForUser(1));
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

        List<Message> messages = userServiceImpl.getAllMessagesSentByUser(1);

        assertEquals(2, messages.size());
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

        List<Message> messages = userServiceImpl.getAllMessageReceivedByUser(1);

        assertEquals(2, messages.size());
    }
}
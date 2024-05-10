package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.exceptions.ResourceCantBeUpdated;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.repositories.InternshipRepository;
import ru.ds.edu.medvedew.internship.services.UserInternshipService;
import ru.ds.edu.medvedew.internship.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class InternshipServiceImplTest {
    @Mock
    private InternshipRepository internshipRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserInternshipService userInternshipService;

    @InjectMocks
    private InternshipServiceImpl internshipServiceImpl;


    @Test
    void getAll() {
        doReturn(List.of(new Internship())).when(internshipRepository).findAll();

        List<Internship> internships = internshipServiceImpl.getAll();

        assertEquals(1, internships.size());
    }

    @Test
    void getById() {
        Internship internship = new Internship();
        internship.setId(1);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        Internship internshipReturned = internshipServiceImpl.getById(1);
        assertEquals(1, internshipReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(internshipRepository).findById(1);

        assertThrows(ResourceNotFoundException.class, () -> internshipServiceImpl.getById(1));
        Mockito.verify(internshipRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void save() {
        Internship toSave = mock(Internship.class);
        Internship internshipToReturn = new Internship();
        internshipToReturn.setId(1);
        doReturn(internshipToReturn).when(internshipRepository).save(toSave);

        Internship saved = internshipServiceImpl.save(toSave);

        assertEquals(1, saved.getId());
    }

    @Test
    void updateExisting() {
        Internship toUpdate = mock(Internship.class);
        Internship internshipToReturn = new Internship();
        internshipToReturn.setId(1);
        doReturn(internshipToReturn).when(internshipRepository).save(toUpdate);
        doReturn(true).when(internshipRepository).existsById(1);

        Internship internship = internshipServiceImpl.update(1, toUpdate);

        assertEquals(1, internship.getId());
    }

    @Test
    void updateNotExisting() {
        Internship toUpdate = mock(Internship.class);
        doReturn(false).when(internshipRepository).existsById(1);

        assertThrows(ResourceNotFoundException.class, () -> internshipServiceImpl.update(1, toUpdate));
        Mockito.verify(internshipRepository, Mockito.times(0)).save(toUpdate);
    }

    @Test
    void delete() {
        doReturn(true).when(internshipRepository).existsById(1);
        internshipServiceImpl.delete(1);
        Mockito.verify(internshipRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void addUserToInternship() {
        Internship internship = new Internship();
        internship.setId(1);
        internship.setApplicationsDeadline(Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant()));
        User userAlreadyParticipant = new User();
        userAlreadyParticipant.setId(1);
        Set<User> participants = new HashSet<>(Set.of(userAlreadyParticipant));
        internship.setParticipants(participants);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        User userToAdd = new User();
        userToAdd.setId(2);
        doReturn(userToAdd).when(userService).getById(2);

        internshipServiceImpl.addUserToInternship(2, 1);

        Mockito.verify(userInternshipService, Mockito.times(1)).save(new UserInternship(userToAdd, internship,
                UserInternshipStatus.SUBMITTED_APPLICATION));
    }

    @Test
    public void addUserToInternship_WithExpiredApplicationsDate_ThrowsException() {
        Internship internship = new Internship();
        internship.setApplicationsDeadline(Date.from(LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault()).toInstant()));
        User userAlreadyParticipant = new User();
        userAlreadyParticipant.setId(1);
        Set<User> participants = new HashSet<>(Set.of(userAlreadyParticipant));
        internship.setParticipants(participants);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);


        assertThrows(ResourceCantBeUpdated.class, () ->
                internshipServiceImpl.addUserToInternship(2, 1));
        assertEquals(1, internship.getParticipants().size());
        assertFalse(internship.getParticipants().stream().anyMatch(u -> u.getId() == 2));
    }

    @Test
    public void addUserToInternship_UserAlreadyParticipant_ThrowsException() {
        Internship internship = new Internship();
        internship.setApplicationsDeadline(Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant()));
        User userAlreadyParticipant = new User();
        userAlreadyParticipant.setId(1);
        Set<User> participants = new HashSet<>(Set.of(userAlreadyParticipant));
        internship.setParticipants(participants);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        assertThrows(ResourceCantBeUpdated.class, () ->
                internshipServiceImpl.addUserToInternship(1, 1));
        assertEquals(1, internship.getParticipants().size());
        assertTrue(internship.getParticipants().stream().anyMatch(u -> u.getId() == 1));
    }

    @Test
    public void getAllParticipants() {
        Internship internship = new Internship();
        User userAlreadyParticipant = new User();
        userAlreadyParticipant.setId(1);
        Set<User> participants = new HashSet<>(Set.of(userAlreadyParticipant));
        internship.setParticipants(participants);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        List<User> users = internshipServiceImpl.getAllParticipants(1);

        assertEquals(1, users.size());
    }

    @Test
    public void getAllLessons() {
        Internship internship = new Internship();
        Lesson internshipLesson = new Lesson();
        internshipLesson.setId(1);
        Set<Lesson> lessons = new HashSet<>(Set.of(internshipLesson));
        internship.setLessons(lessons);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        List<Lesson> lessonsReturned = internshipServiceImpl.getAllLessons(1);

        assertEquals(1, lessonsReturned.size());
    }
}
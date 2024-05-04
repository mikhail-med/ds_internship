package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.exceptions.ResourceCantBeUpdated;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.repositories.InternshipRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class InternshipServiceTest {
    @Autowired
    private InternshipService internshipService;

    @MockBean
    private InternshipRepository internshipRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private UserInternshipService userInternshipService;


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

        internshipService.addUserToInternship(2, 1);

        Mockito.verify(userInternshipService, Mockito.times(1)).save(new UserInternship(userToAdd, internship,
                UserInternshipStatus.SUBMITTED_APPLICATION));
    }

    @Test
    public void addUserToInternshipWithExpiredApplicationsDate() {
        Internship internship = new Internship();
        internship.setApplicationsDeadline(Date.from(LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault()).toInstant()));
        User userAlreadyParticipant = new User();
        userAlreadyParticipant.setId(1);
        Set<User> participants = new HashSet<>(Set.of(userAlreadyParticipant));
        internship.setParticipants(participants);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        User userToAdd = new User();
        userToAdd.setId(2);
        doReturn(userToAdd).when(userService).getById(2);

        assertThrows(ResourceCantBeUpdated.class, () ->
                internshipService.addUserToInternship(2, 1));

        assertEquals(1, internship.getParticipants().size());
        assertFalse(internship.getParticipants().stream().anyMatch(u -> u.getId() == 2));
    }

    @Test
    public void addUserToInternshipAlreadyParticipated() {
        Internship internship = new Internship();
        internship.setApplicationsDeadline(Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant()));
        User userAlreadyParticipant = new User();
        userAlreadyParticipant.setId(1);
        Set<User> participants = new HashSet<>(Set.of(userAlreadyParticipant));
        internship.setParticipants(participants);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        User userToAdd = new User();
        userToAdd.setId(1);
        doReturn(userToAdd).when(userService).getById(1);

        assertThrows(ResourceCantBeUpdated.class, () ->
                internshipService.addUserToInternship(1, 1));

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

        assertEquals(1, internshipService.getAllParticipants(1).size());
    }

    @Test
    public void getAllLessons() {
        Internship internship = new Internship();
        Lesson internshipLesson = new Lesson();
        internshipLesson.setId(1);
        Set<Lesson> lessons = new HashSet<>(Set.of(internshipLesson));
        internship.setLessons(lessons);
        doReturn(Optional.of(internship)).when(internshipRepository).findById(1);

        assertEquals(1, internshipService.getAllLessons(1).size());
    }
}
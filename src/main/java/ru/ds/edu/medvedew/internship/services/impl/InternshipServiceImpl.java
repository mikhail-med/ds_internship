package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceCantBeUpdated;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.Lesson;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.repositories.InternshipRepository;
import ru.ds.edu.medvedew.internship.services.InternshipService;
import ru.ds.edu.medvedew.internship.services.UserInternshipService;
import ru.ds.edu.medvedew.internship.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final UserService userService;
    private final UserInternshipService userInternshipService;

    @Override
    public List<Internship> getAll() {
        return internshipRepository.findAll();
    }

    @Override
    public Internship getById(int id) {
        return internshipRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Internship with id %d not found", id)));
    }

    @Transactional
    @Override
    public Internship save(Internship internship) {
        return internshipRepository.save(internship);
    }

    @Transactional
    @Override
    public Internship update(int id, Internship internship) {
        if (!internshipRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Internship with id %d doesn't exist", id));
        }
        internship.setId(id);
        return internshipRepository.save(internship);
    }

    @Transactional
    @Override
    public void delete(int id) {
        internshipRepository.deleteById(id);
    }

    /**
     * Получить участников стажировки
     *
     * @param internshipId id стажировки
     * @return список участников
     */
    @Override
    public List<User> getAllParticipants(int internshipId) {
        Internship internship = getById(internshipId);
        return new ArrayList<>(internship.getParticipants());
    }

    /**
     * Добавить пользователя на стажировку
     *
     * @param userId       - id пользователя
     * @param internshipId - id стажировки
     */
    @Transactional
    @Override
    public void addUserToInternship(int userId, int internshipId) {
        Internship internship = getById(internshipId);
        checkApplicationsDateNotExpired(internship.getApplicationsDeadline(), internshipId);
        Set<User> participants = internship.getParticipants();

        // проверка что пользователя с таким id ещё нет на стажировке
        if (participants.stream().noneMatch(p -> p.getId() == userId)) {
            User user = userService.getById(userId);
            userInternshipService.save(
                    new UserInternship(user, internship, UserInternshipStatus.SUBMITTED_APPLICATION)
            );
        } else {
            throw new ResourceCantBeUpdated(String.format(
                    "User with id %d is already participating in internship with id %d",
                    userId, internshipId)
            );
        }
    }

    /**
     * Получить все занятия со стажировки
     *
     * @param internshipId - id стажировки
     * @return список занятий
     */
    @Override
    public List<Lesson> getAllLessons(int internshipId) {
        Internship internship = getById(internshipId);
        return new ArrayList<>(internship.getLessons());
    }

    /**
     * проверка того, что срок подачи заявок не истёк
     *
     * @param applicationsDate - дата и время окончания подачи заявок на стажировку
     * @param internshipId     - id стажировки
     */
    private void checkApplicationsDateNotExpired(Date applicationsDate, int internshipId) {
        if (applicationsDate.before(new Date())) {
            throw new ResourceCantBeUpdated(String.format(
                    "The application period for internship with id %d has already expired",
                    internshipId)
            );
        }
    }

}

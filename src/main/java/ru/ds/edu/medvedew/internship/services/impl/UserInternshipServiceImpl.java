package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;
import ru.ds.edu.medvedew.internship.repositories.UserInternshipRepository;
import ru.ds.edu.medvedew.internship.services.UserInternshipService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInternshipServiceImpl implements UserInternshipService {
    private final UserInternshipRepository userInternshipRepository;

    @Override
    public List<UserInternship> getAll() {
        return userInternshipRepository.findAll();
    }

    @Override
    public UserInternship getById(int id) {
        return userInternshipRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User internship with id %d not found", id)));
    }

    @Transactional
    @Override
    public UserInternship save(UserInternship userInternship) {
        return userInternshipRepository.save(userInternship);
    }

    @Transactional
    @Override
    public UserInternship update(int id, UserInternship userInternship) {
        if (!userInternshipRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("User internship with id %d doesn't exists", id));
        }
        userInternship.setId(id);
        return userInternshipRepository.save(userInternship);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userInternshipRepository.deleteById(id);
    }

    @Override
    public List<UserInternship> getAllWithStatus(UserInternshipStatus status) {
        return userInternshipRepository.findByStatus(status);
    }

    @Override
    public List<UserInternship> getAllWithStatusOnInternship(UserInternshipStatus status, int internshipId) {
        return userInternshipRepository.findByStatusAndInternshipId(status, internshipId);
    }
}

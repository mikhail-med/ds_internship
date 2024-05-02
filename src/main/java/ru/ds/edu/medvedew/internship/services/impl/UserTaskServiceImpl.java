package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.UserTask;
import ru.ds.edu.medvedew.internship.repositories.UserTaskRepository;
import ru.ds.edu.medvedew.internship.services.UserTaskService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserTaskServiceImpl implements UserTaskService {
    private final UserTaskRepository userTaskRepository;

    @Override
    public List<UserTask> getAll() {
        return userTaskRepository.findAll();
    }

    @Override
    public UserTask getById(int id) {
        return userTaskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User task with id %d not found", id)));
    }

    @Transactional
    @Override
    public UserTask save(UserTask userTask) {
        return userTaskRepository.save(userTask);
    }

    @Transactional
    @Override
    public UserTask update(int id, UserTask userTask) {
        if (!userTaskRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("User task with id %d doesn't exists", id));
        }
        userTask.setId(id);
        return userTaskRepository.save(userTask);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userTaskRepository.deleteById(id);
    }
}

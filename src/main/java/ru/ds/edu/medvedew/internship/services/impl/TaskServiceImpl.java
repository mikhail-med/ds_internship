package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Task;
import ru.ds.edu.medvedew.internship.repositories.TaskRepository;
import ru.ds.edu.medvedew.internship.services.TaskService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(int id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Task with id %d not found", id)));
    }

    @Transactional
    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public Task update(int id, Task task) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Task with id %d doesn't exists", id));
        }
        task.setId(id);
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public void delete(int id) {
        taskRepository.deleteById(id);
    }
}

package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.repositories.InternshipRepository;
import ru.ds.edu.medvedew.internship.services.InternshipService;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;

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
}

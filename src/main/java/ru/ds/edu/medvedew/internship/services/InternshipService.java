package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Internship;

import java.util.List;

/**
 * Сервис для стажировок
 */
public interface InternshipService {
    List<Internship> getAll();

    Internship getById(int id);

    Internship save(Internship internship);

    Internship update(int id, Internship internship);

    void delete(int id);
}

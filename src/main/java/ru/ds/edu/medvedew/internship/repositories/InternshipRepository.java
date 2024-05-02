package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ds.edu.medvedew.internship.models.Internship;

public interface InternshipRepository extends JpaRepository<Internship, Integer> {
}

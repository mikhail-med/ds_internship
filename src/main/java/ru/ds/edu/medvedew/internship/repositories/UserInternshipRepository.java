package ru.ds.edu.medvedew.internship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ds.edu.medvedew.internship.models.UserInternship;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;

import java.util.List;

public interface UserInternshipRepository extends JpaRepository<UserInternship, Integer> {
    List<UserInternship> findByStatus(UserInternshipStatus status);

    List<UserInternship> findByStatusAndInternshipId(UserInternshipStatus status, int internshipId);
}

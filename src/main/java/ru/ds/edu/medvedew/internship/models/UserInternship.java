package ru.ds.edu.medvedew.internship.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ds.edu.medvedew.internship.models.statuses.UserInternshipStatus;

import javax.persistence.*;

@Entity
@Table(name = "user_internships")
@Data
@NoArgsConstructor
public class UserInternship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "internship_id", referencedColumnName = "id")
    private Internship internship;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserInternshipStatus status;

    public UserInternship(User user, Internship internship, UserInternshipStatus status) {
        this.user = user;
        this.internship = internship;
        this.status = status;
    }
}

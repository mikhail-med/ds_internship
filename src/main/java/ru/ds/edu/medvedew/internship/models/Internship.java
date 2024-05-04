package ru.ds.edu.medvedew.internship.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ds.edu.medvedew.internship.models.statuses.InternshipStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Класс представляющий стажировку
 */
@Entity
@Table(name = "internship")
@Data
public class Internship {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    /**
     * Последний день и время приёма заявок
     */
    @Column(name = "applications_deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationsDeadline;

    @OneToMany(mappedBy = "internship")
    private Set<Lesson> lessons;

    @OneToMany(mappedBy = "internship")
    @EqualsAndHashCode.Exclude
    private List<UserInternship> users;

    @ManyToMany
    @JoinTable(name = "user_internships",
            joinColumns = @JoinColumn(name = "internship_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @EqualsAndHashCode.Exclude
    private Set<User> participants;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InternshipStatus status;
}

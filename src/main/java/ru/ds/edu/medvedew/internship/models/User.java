package ru.ds.edu.medvedew.internship.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Пользователь
 */
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number")),
            @AttributeOverride(name = "telegramId", column = @Column(name = "telegram_id"))
    })
    private UserContacts contacts;

    @Column(name = "username")
    private String username;

    @Column(name = "info")
    private String information;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "city")
    private String city;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "status", column = @Column(name = "edu_status"))}
    )
    private Education education;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private Set<Role> roles;

    @ManyToMany(mappedBy = "participants")
    @EqualsAndHashCode.Exclude
    private Set<Internship> internships;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<UserTask> userTasks;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<UserInternship> userInternships;

    @OneToMany(mappedBy = "sender")
    @EqualsAndHashCode.Exclude
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "consumer")
    @EqualsAndHashCode.Exclude
    private Set<Message> receivedMessages;
}

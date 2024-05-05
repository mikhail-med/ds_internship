package ru.ds.edu.medvedew.internship.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

/**
 * Занятия
 */
@Entity
@Table(name = "lesson")
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "internship_id", referencedColumnName = "id")
    private Internship internship;

    @OneToMany(mappedBy = "lesson")
    @EqualsAndHashCode.Exclude
    private Set<Task> task;
}

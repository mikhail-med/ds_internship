package ru.ds.edu.medvedew.internship.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Задача занятия
 */
@Entity
@Table(name = "task")
@Data
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "repo")
    private String repository;

    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @OneToMany(mappedBy = "task")
    private Set<UserTask> userTasks;

}

package ru.ds.edu.medvedew.internship.models;

import lombok.Data;
import ru.ds.edu.medvedew.internship.models.statuses.TaskStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Информация о результате проверки задачи, которую решил пользователь
 */
@Entity
@Table(name = "user_tasks")
@Data
public class UserTask {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "on_moment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date onMoment;

    @Column(name = "commit_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commitCreatedAt;
}

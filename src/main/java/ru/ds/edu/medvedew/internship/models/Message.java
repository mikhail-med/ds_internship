package ru.ds.edu.medvedew.internship.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Сообщения
 */
@Entity
@Table(name = "message")
@Data
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Отправитель
     */
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    /**
     * Получатель
     */
    @ManyToOne
    @JoinColumn(name = "consumer_id", referencedColumnName = "id")
    private User consumer;

    /**
     * Содержимое
     */
    @Column(name = "message")
    private String message;

    /**
     * Время отправки
     */
    @Column(name = "on_moment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date onMoment;
}

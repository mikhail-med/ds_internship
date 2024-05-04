package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * При запросе некоторого списка пользователей скорее всего нет смысла возвращать
 * данные об образовании, дату рождения и тп,
 * так что существует этот класс который включает небольшой набор полей
 */
@ApiModel("Малое кол-во информации о пользователе")
@Data
public class UserSmallDto {
    private int id;
    private String name;
    private String username;
}

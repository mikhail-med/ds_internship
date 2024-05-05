package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Если сообщение создаёт админ, то можно использовать MessageDto,
 * чтобы получить большую свободу для входных данных, но если сообщение
 * создаёт пользователь, то ему необходимо передать только содержимое сообщения,
 * а, например, время сообщения выставится на сервере.
 */
@ApiModel("Текст сообщения")
@Data
public class MessageTextDto {
    private String text;
}

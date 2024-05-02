package ru.ds.edu.medvedew.internship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("Сообщение")
@Data
public class MessageDto {
    private int id;
    @ApiModelProperty("Id отправителя")
    private int senderId;
    @ApiModelProperty("Id получателя")
    private int consumerId;
    private String message;
    @ApiModelProperty("Время отправки сообщения")
    private Date onMoment;
}

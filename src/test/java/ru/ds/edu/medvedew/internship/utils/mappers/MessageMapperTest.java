package ru.ds.edu.medvedew.internship.utils.mappers;

import org.junit.jupiter.api.Test;
import ru.ds.edu.medvedew.internship.dto.MessageDto;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.models.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageMapperTest {
    private MessageMapper messageMapper = MessageMapper.MAPPER;

    @Test
    void toMessageDto() {
        Message message = new Message();
        message.setId(1);
        User sender = new User();
        sender.setId(2);
        message.setSender(sender);
        User consumer = new User();
        consumer.setId(3);
        message.setConsumer(consumer);

        MessageDto messageDto = messageMapper.toMessageDto(message);

        assertEquals(1, messageDto.getId());
        assertEquals(2, messageDto.getSenderId());
        assertEquals(3, messageDto.getConsumerId());
    }

    @Test
    void toMessage() {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(1);
        messageDto.setSenderId(2);
        messageDto.setConsumerId(3);

        Message message = messageMapper.toMessage(messageDto);

        assertEquals(1, message.getId());
        assertEquals(2, message.getSender().getId());
        assertEquals(3, message.getConsumer().getId());
    }
}
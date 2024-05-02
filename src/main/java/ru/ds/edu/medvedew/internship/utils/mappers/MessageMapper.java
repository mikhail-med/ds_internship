package ru.ds.edu.medvedew.internship.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.ds.edu.medvedew.internship.dto.MessageDto;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.models.User;

/**
 * Mapper для сообщения и dto
 */
@Mapper
public interface MessageMapper {
    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "senderId", expression = "java(message.getSender().getId())")
    @Mapping(target = "consumerId", expression = "java(message.getConsumer().getId())")
    MessageDto toMessageDto(Message message);

    @Mapping(source = "senderId", target = "sender", qualifiedByName = "userObject")
    @Mapping(source = "consumerId", target = "consumer", qualifiedByName = "userObject")
    Message toMessage(MessageDto messageDto);

    @Named("userObject")
    default User toUserObject(int userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }


}

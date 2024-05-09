package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.repositories.MessageRepository;
import ru.ds.edu.medvedew.internship.services.MessageService;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message getById(int id) {
        return messageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Message with id %d not found", id)));
    }

    @Transactional
    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Transactional
    @Override
    public Message update(int id, Message message) {
        checkMessageExists(id);
        message.setId(id);
        return messageRepository.save(message);
    }

    @Transactional
    @Override
    public void delete(int id) {
        checkMessageExists(id);
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> getAllMessagesBetweenUsers(int firstUserId, int secondUserId) {
        return messageRepository.findAllBetweenUsers(firstUserId, secondUserId);
    }

    @Transactional
    @Override
    public Message sendMessageFromUserToUserWithText(int senderId, int consumerId, String text) {
        Message message = new Message();
        User sender = new User();
        sender.setId(senderId);
        message.setSender(sender);

        User consumer = new User();
        consumer.setId(consumerId);
        message.setConsumer(consumer);

        message.setMessage(text);
        message.setOnMoment(new Date());
        return messageRepository.save(message);
    }

    private void checkMessageExists(int messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new ResourceNotFoundException(String.format("Message with id %d doesn't exists", messageId));
        }
    }

}

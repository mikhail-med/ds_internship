package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.repositories.MessageRepository;
import ru.ds.edu.medvedew.internship.services.MessageService;

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
        if (!messageRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Message with id %d doesn't exists", id));
        }
        message.setId(id);
        return messageRepository.save(message);
    }

    @Transactional
    @Override
    public void delete(int id) {
        messageRepository.deleteById(id);
    }
}

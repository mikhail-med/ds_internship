package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.models.Internship;
import ru.ds.edu.medvedew.internship.models.Message;
import ru.ds.edu.medvedew.internship.models.Role;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.repositories.UserRepository;
import ru.ds.edu.medvedew.internship.services.RoleService;
import ru.ds.edu.medvedew.internship.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id %d not found", id)));
    }

    @Transactional
    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User update(int id, User user) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("User with id %d doesn't exists", id));
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Message> getAllMessagesForUser(int userId) {
        User user = getById(userId);
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(user.getSentMessages());
        allMessages.addAll(user.getReceivedMessages());
        return allMessages;
    }

    @Override
    public List<Message> getAllMessagesSentByUser(int userId) {
        User user = getById(userId);
        return new ArrayList<>(user.getSentMessages());
    }

    @Override
    public List<Message> getAllMessageReceivedByUser(int userId) {
        User user = getById(userId);
        return new ArrayList<>(user.getReceivedMessages());
    }

    @Override
    public List<Internship> getAllUserInternships(int id) {
        User user = getById(id);
        return new ArrayList<>(user.getInternships());
    }

    @Transactional
    @Override
    public void addRoleToUser(int roleId, int userId) {
        User user = getById(userId);
        Role role = roleService.getById(roleId);
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        userRepository.save(user);
    }

}

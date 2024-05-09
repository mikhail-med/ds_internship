package ru.ds.edu.medvedew.internship.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.services.UserService;

import java.util.Optional;

/**
 * Проверят то, что пользователь хочет изменить данные принадлежащие этому пользователю
 */
@Component("userIdValidator")
@RequiredArgsConstructor
@Slf4j
public class UserIdValidator {
    private final UserService userService;

    public boolean isSameId(Authentication authentication, Integer id) {
        Optional<User> authenticatedUser = userService.findByUsername(authentication.getName());
        if (authenticatedUser.isEmpty()) {
            log.warn("Anonymous user tried to access data of user with id {}", id);
            return false;
        }

        return id == authenticatedUser.get().getId();
    }
}

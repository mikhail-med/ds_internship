package ru.ds.edu.medvedew.internship.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ds.edu.medvedew.internship.exceptions.ResourceNotFoundException;
import ru.ds.edu.medvedew.internship.repositories.UserRepository;

/**
 * Проверят то, что пользователь хочет изменить данные принадлежащие этому пользователю
 */
@Component("userIdValidator")
@RequiredArgsConstructor
public class UserIdValidator {
    private final UserRepository userRepository;

    public boolean isSameId(Authentication authentication, Integer id) {
        // ControllerAdvice с ExceptionHandler не поймают это исключение,
        // так что ещё подумаю как сделать
        int authenticatedUserId = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                        new ResourceNotFoundException(String.format("User with username %s not found",
                                authentication.getName())))
                .getId();

        return id == authenticatedUserId;
    }
}

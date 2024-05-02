package ru.ds.edu.medvedew.internship.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.models.Role;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.models.statuses.UserStatus;
import ru.ds.edu.medvedew.internship.repositories.UserRepository;

import static ru.ds.edu.medvedew.internship.models.statuses.UserStatus.EXPELLED;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with username %s not found", username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .accountLocked(isUserAccountLocked(user.getStatus()))
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .toArray(String[]::new))
                .build();
    }

    private boolean isUserAccountLocked(UserStatus userStatus) {
        return userStatus == EXPELLED;
    }
}

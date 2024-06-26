package ru.ds.edu.medvedew.internship.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ds.edu.medvedew.internship.services.impl.UserAuthDetailsService;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserAuthDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
                .antMatchers("/v1/users/{userId}",
                        "/v1/internships/{id}/user/{userId}",
                        "/v1/users/{userId}/messages",
                        "/v1/users/{userId}/sent-messages",
                        "/v1/users/{userId}/received-messages",
                        "/v1/messages/{userId}/chat-with/{secondUserId}",
                        "/v1/messages/{userId}/chat-to/{secondUserId}",
                        "/v1/users/{userId}/internships",
                        "/v1/internships/{internshipId}/progress/user/{userId}")
                .access("hasRole('ADMIN') or @userIdValidator.isSameId(authentication,#userId)")
                .anyRequest().hasRole("ADMIN")
                .and().httpBasic()
                .and()
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

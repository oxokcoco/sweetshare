package com.sweetshare.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import com.sweetshare.common.error.ApiException;
import com.sweetshare.common.error.ErrorCode;

class UserServiceConflictTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository,
            PasswordEncoderFactories.createDelegatingPasswordEncoder());

    @Test
    void concurrentDuplicateEmailBecomesEmailTaken() {
        when(userRepository.saveAndFlush(any()))
                .thenThrow(new DataIntegrityViolationException(
                        "duplicate key value violates unique constraint \"users_email_unique\""));

        ApiException ex = assertThrows(ApiException.class,
                () -> userService.register("ste@x.com", "steven", "password"));
        assertEquals(ErrorCode.EMAIL_TAKEN, ex.errorCode());
    }

    @Test
    void concurrentDuplicateUsernameBecomesUsernameTaken() {
        when(userRepository.saveAndFlush(any()))
                .thenThrow(new DataIntegrityViolationException(
                        "duplicate key value violates unique constraint \"users_username_unique\""));

        ApiException ex = assertThrows(ApiException.class,
                () -> userService.register("ste@x.com", "steven", "password"));
        assertEquals(ErrorCode.USERNAME_TAKEN, ex.errorCode());
    }

    @Test
    void concurrentUnknownConstraint() {
        when(userRepository.saveAndFlush(any()))
                .thenThrow(new DataIntegrityViolationException(
                        "some other constraint \"some_other_constraint\""));

        assertThrows(DataIntegrityViolationException.class,
                () -> userService.register("ste@x.com", "steven", "password"));
    }
}
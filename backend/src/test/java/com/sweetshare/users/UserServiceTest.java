package com.sweetshare.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.sweetshare.common.error.ApiException;
import com.sweetshare.common.error.ErrorCode;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void lowerCaseEmailUsernameOnRegister() {
        User user = userService.register("SteVen@x.com", "sTEVeN", "password");

        assertEquals("steven@x.com", user.getEmail());
        assertEquals("steven", user.getUsername());
    }

    @Test
    void hashedPassword() {
        User user = userService.register("SteVen@x.com", "sTEVeN", "password");
        assertNotEquals("password", user.getPasswordHash());
        assertTrue(passwordEncoder.matches("password", user.getPasswordHash()));
    }

    @Test
    void sameEmailRegister() {
        userService.register("SteVen@x.com", "sTEVeN", "password");

        ApiException ex = assertThrows(ApiException.class,
                () -> userService.register("steven@x.com", "ste", "password"));

        assertEquals(ErrorCode.EMAIL_TAKEN, ex.errorCode());
    }

    @Test
    void sameUsernameRegister() {
        userService.register("SteVen@x.com", "sTEVeN", "password");
        ApiException ex = assertThrows(ApiException.class,
                () -> userService.register("ste@x.com", "steven", "password"));
        assertEquals(ErrorCode.USERNAME_TAKEN, ex.errorCode());
    }
}

package com.sweetshare.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAssignIdDefaultRoleAndTimestamps() throws Exception {
        User user = new User("steven@x.com", "steven", "hash");
        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals(Role.USER, saved.getRole());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());

    }

    @Test 
    void existsByEmailFindsSavedUserOnly() throws Exception {
        String email = "chicken@y.com";
        User user = new User(email, "chicken", "chichekHash");
        userRepository.save(user);

        assertTrue(userRepository.existsByEmail(email));
        assertFalse(userRepository.existsByEmail("nobody@x.com"));

    }

}

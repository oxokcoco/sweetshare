package com.sweetshare.users;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sweetshare.common.error.ApiException;
import com.sweetshare.common.error.ErrorCode;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String email, String username, String rawPassword) {
        String emailLower = email.toLowerCase(Locale.ROOT).strip();
        String usernameLower = username.toLowerCase(Locale.ROOT).strip();

        if (userRepository.existsByEmail(emailLower)) {
            throw new ApiException(ErrorCode.EMAIL_TAKEN);
        } else if (userRepository.existsByUsername(usernameLower)) {
            throw new ApiException(ErrorCode.USERNAME_TAKEN);
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(emailLower, usernameLower, encodedPassword);
        return userRepository.save(user);
    }
}

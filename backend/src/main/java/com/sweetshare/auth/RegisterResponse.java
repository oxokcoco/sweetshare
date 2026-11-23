package com.sweetshare.auth;

import com.sweetshare.users.User;

record RegisterResponse(Long id, String username, String email) {
    static RegisterResponse from(User user) {
        return new RegisterResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}

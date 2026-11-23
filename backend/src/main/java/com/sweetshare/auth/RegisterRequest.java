package com.sweetshare.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

record RegisterRequest(
    @NotBlank  @Email @Size(max=254) String email, 
    @NotBlank @Pattern(regexp = "^[A-Za-z0-9_]{3,30}$") String username, 
    @NotBlank @Size(min=8, max=72) String password) {

}

package com.sweetshare.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private static final String[] PUBLIC_ENDPOINTS = { "/actuator/health", "/api/v1/auth/csrf" };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JsonAuthenticationEntryPoint authEntryPoint,
            JsonAccessDeniedHandler jsonAccessDeniedHandler)
            throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated())
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .exceptionHandling(
                        ex -> ex.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(jsonAccessDeniedHandler));
        return http.build();
    }

    @Bean 
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

package com.sweetshare.auth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import tools.jackson.databind.json.JsonMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void userCanRegister() throws Exception {
        String body = jsonMapper.writeValueAsString(new RegisterRequest("SteVen@X.com", "sTevEn", "Password123"));
        mockMvc.perform(
                post("/api/v1/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("steven"))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    private ResultActions register(String body) throws Exception {
       return mockMvc.perform(
                post("/api/v1/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
    }

    @Test
    void userCanOnlyRegisterOnce() throws Exception {
        String body = jsonMapper.writeValueAsString(new RegisterRequest("SteVen@X.com", "sTevEn", "Password123"));

        register(body)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("steven"))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());

        register(body)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.code").value("EMAIL_TAKEN"));
    }
}

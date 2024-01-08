package com.ace.xpresspayment.controller;

import com.ace.xpresspayment.dtos.request.LoginRequest;
import com.ace.xpresspayment.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void authorize() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("12345678A1@j");

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        String mockToken = "mockAccessToken";

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenProvider.generateJWTToken(authentication)).thenReturn(mockToken);

        mockMvc.perform(post("/authorize")
                        .contentType("application/json")
                        .content("{\"email\":\"test@example.com\",\"password\":\"12345678A1@j\"}"))
                .andExpect(status().isOk());
    }

//    @Test
//    void authorize_InvalidCredentials_ShouldReturnBadRequest() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("test@example.com");
//        loginRequest.setPassword("wrong_password"); // Incorrect password
//
//        when(authenticationManager.authenticate(any())).thenThrow(org.springframework.security.authentication.BadCredentialsException.class);
//
//        mockMvc.perform(post("/authorize")
//                        .contentType("application/json")
//                        .content("{\"email\":\"test@example.com\",\"password\":\"wrong_password\"}"))
//                .andExpect(status().isUnauthorized());
//    }
}

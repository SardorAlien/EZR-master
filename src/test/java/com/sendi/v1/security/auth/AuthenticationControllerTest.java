package com.sendi.v1.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.jwt.JwtAuthenticationFilter;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.security.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(AuthenticationController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    private final static String BASE_URL_AUTH = "/api/v1/auth";

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private RegisterRequest registerRequest;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtService jwtService;

//    @MockBean
//    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    JpaUserDetailsService jpaUserDetailsService;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleService roleService;

    @MockBean
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .firstname("sardor")
                .lastname("jumamuratov")
                .password("5938240s")
                .email("sardor1441@gmail.com")
                .username("saju1441")
                .build();
    }

    @Test
    void register() throws Exception {
        System.out.println(userService);
        System.out.println(authenticationService);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken("123123");

        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authenticationResponse);
        System.out.println("token => " + authenticationService.register(registerRequest).getToken());
//        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

//        when(authenticationService.register(any(RegisterRequest.class)))
//                .thenReturn(authenticationResponse);


//        mockMvc.perform(post(BASE_URL_AUTH + "/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isOk());
    }

    @Test
    void authenticate() {
    }
}
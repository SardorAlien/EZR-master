package com.sendi.v1.security.jwt;

import com.sendi.v1.security.service.JpaUserDetailsService;
import com.sendi.v1.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;

    @Mock
    private AuthenticationManager authenticationManager;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private JpaUserDetailsService jpaUserDetailsService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, jpaUserDetailsService);
    }

//    @Test
//    public void throwsWithoutToken() throws Exception {
//        JwtAuthenticationFilter spyJwt = Mockito.spy(jwtAuthenticationFilter);
//        when(httpServletRequest.getServletPath()).thenReturn("/api");
//
//        spyJwt.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
//
//        verify(spyJwt, never());
//    }

}
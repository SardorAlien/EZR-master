package com.sendi.v1.security.auth;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationResponse {
    private String token;
}

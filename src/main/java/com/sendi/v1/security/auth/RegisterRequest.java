package com.sendi.v1.security.auth;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;

}

package com.sendi.v1.security.auth;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationRequest {
    @NotBlank
    @NotNull
    @Size(min = 5, message = "username cannot be less than 5 characters")
    private String username;

    @NotNull
    @NotBlank
    @Pattern(message = "Your password should have at least one capital letter, one lower-case letter, one digit and one symbol. " +
            "That way your password will be stronger",
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z0-9!@#$%^&*()_+]{8,}$")
    @Size(min = 8)
    private String password;
}

package com.sendi.v1.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sendi.v1.domain.Deck;
import com.sendi.v1.security.domain.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private Set<Role> roles;
    private Set<Deck> decks;
    private LocalDateTime createdAt;
}

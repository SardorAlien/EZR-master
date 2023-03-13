package com.sendi.v1.dto;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.security.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private Set<Role> roles;
    private Set<Deck> decks;
}

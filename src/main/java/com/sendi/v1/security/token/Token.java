package com.sendi.v1.security.token;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sendi.v1.domain.BaseEntity;
import com.sendi.v1.security.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity(name = "tokens")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Token extends BaseEntity {
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private Boolean expired;

    private Boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private User user;
}

package com.sendi.v1.security.token;


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
    private TokenType tokenType;

    private Boolean expired;

    private Boolean revoked;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

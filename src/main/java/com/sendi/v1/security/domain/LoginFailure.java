package com.sendi.v1.security.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sendi.v1.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Table(name = "login_failure")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginFailure extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    private String username;

    @Column(name = "source_ip")
    private String sourceIP;
}

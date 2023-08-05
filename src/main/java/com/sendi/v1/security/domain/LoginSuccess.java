package com.sendi.v1.security.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sendi.v1.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Table(name = "login_success")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginSuccess extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @Column(name = "source_ip")
    private String sourceIP;
}

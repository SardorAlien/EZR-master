package com.sendi.v1.security.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sendi.v1.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @JsonBackReference
    private Set<User> users;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Authority> authorities;
}

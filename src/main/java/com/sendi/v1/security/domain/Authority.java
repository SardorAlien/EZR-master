package com.sendi.v1.security.domain;

import com.sendi.v1.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Authority extends BaseEntity {

     private String permission;

     @ManyToMany(mappedBy = "authorities")
     private Set<Role> roles;
}


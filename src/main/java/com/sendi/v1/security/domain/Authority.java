package com.sendi.v1.security.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sendi.v1.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
     @JsonBackReference
     private Set<Role> roles;

     @Override
     public boolean equals(Object o) {
          return super.equals(o);
     }

     @Override
     public int hashCode() {
          return super.hashCode();
     }
}


package com.sendi.v1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sendi.v1.security.domain.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "images")
public class Image extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1928961392414087471L;

    private String name;
    private Long size;
    private String contentType;

    @Lob
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Flashcard flashcard;
}

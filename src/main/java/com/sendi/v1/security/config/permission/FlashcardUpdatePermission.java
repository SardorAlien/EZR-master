package com.sendi.v1.security.config.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('flashcard.update') AND @flashcardAuthenticationManager.idMatchesFlashcard(authentication, #deckId)")
public @interface FlashcardUpdatePermission {
}

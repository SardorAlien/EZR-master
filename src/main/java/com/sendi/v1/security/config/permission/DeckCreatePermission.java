package com.sendi.v1.security.config.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('deck.create') AND @deckAuthenticationManager.idMatchesDeck(authentication, #userId)")
public @interface DeckCreatePermission {
}

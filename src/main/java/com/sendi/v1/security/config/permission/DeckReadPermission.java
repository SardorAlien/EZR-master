package com.sendi.v1.security.config.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;


//@PreAuthorize("hasAuthority('deck.read')")

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('deck.read') AND @deckAuthenticationManager.idMatchesDeck(authentication, #userId)")
public @interface DeckReadPermission {

}

package com.sendi.v1.service;

import com.sendi.v1.email.EmailService;
import com.sendi.v1.security.service.UserService;
import com.sendi.v1.service.model.ShareWith;
import com.sendi.v1.service.model.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class ShareDeckService {
    private final EmailService emailService;
    private final UserService userService;
    private final DeckService deckService;

    public ShareDeckService(EmailService emailService, UserService userService, DeckService deckService) {
        this.emailService = emailService;
        this.userService = userService;
        this.deckService = deckService;
    }

    public String shareDeckWithEmail(Long userId, ShareWith shareWith, Long deckId) {
        UserDTO user = userService.getUserById(userId);
        String link = "http:/localhost:8081/api/v1/decks/";
        String subject = String.format("Learn this study deck with %s on EZR.", user.getUsername());
        String body = String.format("Hello!\n\n I hope you are doing great!\n\n " +
                        "I think It's high time to boost your learning with this study deck - %s!\n\n Study with this link:%s",
                deckService.getOneById(deckId).getName()
                ,
                link);
        emailService.sendMail(shareWith.getToEmail(), subject, body);
        return "Success";
    }
}

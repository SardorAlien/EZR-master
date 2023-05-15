package com.sendi.v1.controller.IT;

import com.sendi.v1.service.dto.DeckDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeckControllerIT {

    private final static String BASE_URL_DECKS = "/api/v1/decks";

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void getAllDecks() {
        List<DeckDTO> deckDTOList =
                restTemplate.getForObject(BASE_URL_DECKS + "/{userId}/all", List.class, 17);

        assertThat(deckDTOList).isEmpty();
    }

}

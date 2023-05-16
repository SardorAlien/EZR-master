package com.sendi.v1.controller.IT;

import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.service.dto.DeckDTO;
import com.sendi.v1.service.dto.mapper.DeckMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeckControllerIT {

    private final static String BASE_URL_DECKS = "/api/v1/decks/";

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRestTemplate restTemplate;

    User user;

    @MockBean
    DeckMapper deckMapper;

    @BeforeEach
    void setUp() {
        user = userRepository.findAll().get(0);
    }

    @Test
    void getAllDecks() {
//        List<DeckDTO> deckDTOList =
//                restTemplate.getForObject(, List.class, 17);
        String url = BASE_URL_DECKS + user.getId() + "/all";
        List<DeckDTO> deckDTOList = restTemplate.getForObject(url, List.class);

        assertThat(deckDTOList).isEmpty();
    }

}

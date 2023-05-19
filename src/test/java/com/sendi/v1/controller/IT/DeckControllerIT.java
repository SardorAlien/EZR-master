package com.sendi.v1.controller.IT;

import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.service.dto.DeckDTO;
import com.sendi.v1.service.dto.mapper.DeckMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DeckControllerIT {

    @LocalServerPort
    private int port;

//    private final static String BASE_URL_DECKS = "http://localhost:";

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRestTemplate restTemplate;

    User user;

    HttpHeaders headers;

    @BeforeEach
    void setUp() {
        user = userRepository.findAll().get(0);
        System.out.println(user.getId());
        headers = new HttpHeaders();
    }


    @WithMockUser(authorities = "deck.read")
    @Test
    void getAllDecks() throws JSONException {
//        List<DeckDTO> deckDTOList =
//                restTemplate.getForObject(createURLWithPort(user.getId() + "/all"), List.class);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(user.getId() + "/all"),
                HttpMethod.GET, entity, String.class);

//        String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";
//
//        JSONAssert.assertEquals(expected, response.getBody(), false);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/v1/decks/" + uri;
    }

}

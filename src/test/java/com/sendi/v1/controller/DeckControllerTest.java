package com.sendi.v1.controller;

import com.sendi.v1.domain.Deck;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(DeckController.class)
class DeckControllerTest extends AbstractControllerTest {

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    public void getAllDecksByUser() throws Exception {
        String URI = "api/v1/deck";

        MvcResult mvcResult = mock.perform(MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();

        Set<Deck> decks = super.mapFromJson(content, Set.class);
        assertTrue(decks.size() > 0);
    }

}
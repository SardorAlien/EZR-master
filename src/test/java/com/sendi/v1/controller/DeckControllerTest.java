package com.sendi.v1.controller;

import com.sendi.v1.domain.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(DeckController.class)
@Disabled
class DeckControllerTest extends AbstractControllerTest {

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    public void getAllDecksByUser() throws Exception {
        String URI = "http://localhost:8080/api/v1/deck";

        MvcResult mvcResult = mock.perform(MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();

        Set<Deck> decks = super.mapFromJson(content, Set.class);
        assertTrue(decks.size() > 0);
    }

}
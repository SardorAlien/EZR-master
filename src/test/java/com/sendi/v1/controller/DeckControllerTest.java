package com.sendi.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.security.service.JpaUserDetailsService;
import com.sendi.v1.security.service.JwtService;
import com.sendi.v1.service.DeckService;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(DeckController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class DeckControllerTest {

    private final static String BASE_URL_DECKS = "/api/v1/decks";

    @MockBean
    DeckService deckService;

    @Autowired
    MockMvc mockMvc;

    private DeckDTO deckDTO;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtService jwtService;

    @MockBean
    JpaUserDetailsService jpaUserDetailsService;

    @BeforeEach
    void setUp() {
        deckDTO = new DeckDTO();
        deckDTO.setId(1L);
        deckDTO.setName("deck1");
        deckDTO.setDescription("desc1");
        deckDTO.setFlashcardDTOs(Set.of(new FlashcardDTO(), new FlashcardDTO()));
    }

    @WithMockUser(authorities = "deck.read")
    @Test
    void getAllDecks() throws Exception {
        when(deckService.getDecksByUserId(anyLong()))
                .thenReturn(List.of(deckDTO, deckDTO));

        ResultActions response = mockMvc.perform(get(BASE_URL_DECKS + "/{userId}/all", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(2)));

        verify(deckService, times(1)).getDecksByUserId(anyLong());
    }

    @WithMockUser(authorities = "deck.read")
    @Test
    void getAllDecksWhenUserDoesNotExist() throws Exception {
        when(deckService.getDecksByUserId(2L))
                .thenThrow(NoSuchUserException.class);

        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL_DECKS + "/{userId}/all", 2L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(deckService, times(1)).getDecksByUserId(anyLong());
    }

    @WithMockUser(authorities = "deck.create")
    @Test
    void createDeckByUserId() throws Exception {
        when(deckService.createOrUpdate(anyLong(), any(DeckDTO.class)))
                .thenReturn(deckDTO);

        ResultActions response = mockMvc.perform(post(BASE_URL_DECKS + "/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(deckDTO)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(deckDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(deckDTO.getName())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(deckDTO.getDescription())))
                .andExpect(jsonPath("$.flashcardDTOs", hasSize(2)));

        verify(deckService, times(1)).createOrUpdate(anyLong(), any(DeckDTO.class));
    }

    @WithMockUser(authorities = "deck.create")
    @Test
    void createDeckByUserIdWhenDoesNotExist() throws Exception {
        when(deckService.createOrUpdate(anyLong(), any(DeckDTO.class)))
                .thenThrow(NoSuchUserException.class);

        MockHttpServletResponse response = mockMvc.perform(post(BASE_URL_DECKS + "/{userId}", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(deckDTO)))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(deckService, times(1)).createOrUpdate(anyLong(), any(DeckDTO.class));
    }

    @WithMockUser(authorities = "deck.update")
    @Test
    void updateDeckByUserId() throws Exception {
        when(deckService.createOrUpdate(anyLong(), any(DeckDTO.class)))
                .thenReturn(deckDTO);

        ResultActions response = mockMvc.perform(put(BASE_URL_DECKS + "/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(deckDTO)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(deckDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(deckDTO.getName())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(deckDTO.getDescription())))
                .andExpect(jsonPath("$.flashcardDTOs", hasSize(2)));

        verify(deckService, times(1)).createOrUpdate(anyLong(), any(DeckDTO.class));
    }

    @WithMockUser(authorities = "deck.update")
    @Test
    void updateDeckByUserIdWhenDoesNotExist() throws Exception {
        when(deckService.createOrUpdate(anyLong(), any(DeckDTO.class)))
                .thenThrow(NoSuchUserException.class);

        MockHttpServletResponse response = mockMvc.perform(put(BASE_URL_DECKS + "/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(deckDTO)))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(deckService, times(1)).createOrUpdate(anyLong(), any(DeckDTO.class));
    }

    @WithMockUser(authorities = "deck.delete")
    @Test
    void deleteDeck() throws Exception {
        doNothing().when(deckService).deleteById(anyLong());

        ResultActions response = mockMvc.perform(delete(BASE_URL_DECKS + "/{deckId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());

        verify(deckService).deleteById(anyLong());
    }

    @WithMockUser(authorities = "deck.read")
    @Test
    void getDeck() throws Exception {
        when(deckService.getOneById(anyLong())).thenReturn(deckDTO);

        ResultActions response = mockMvc.perform(get(BASE_URL_DECKS + "/{deckId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(deckDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(deckDTO.getName())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(deckDTO.getDescription())))
                .andExpect(jsonPath("$.flashcardDTOs", hasSize(2)));

        verify(deckService, times(1)).getOneById(anyLong());
    }

    @WithMockUser(authorities = "deck.read")
    @Test
    void getDeckWhenDoesNotExist() throws Exception {
        when(deckService.getOneById(anyLong())).thenThrow(NoSuchDeckException.class);

        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL_DECKS + "/{deckId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(deckService, times(1)).getOneById(anyLong());
    }
}
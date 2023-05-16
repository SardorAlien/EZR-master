package com.sendi.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.exception.custom.NoSuchFlashcardException;
import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.security.service.JpaUserDetailsService;
import com.sendi.v1.security.service.JwtService;
import com.sendi.v1.service.FlashcardService;
import com.sendi.v1.service.dto.FlashcardDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlashcardController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class FlashcardControllerTest {

    private final static String BASE_URL_FLASHCARDS = "/api/v1/flashcards";

    @MockBean
    FlashcardService flashcardService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtService jwtService;

    @MockBean
    JpaUserDetailsService jpaUserDetailsService;

    FlashcardDTO flashcardDTO;

    @BeforeEach
    void setUp() {
        flashcardDTO = new FlashcardDTO();
        flashcardDTO.setId(1L);
        flashcardDTO.setTerm("term1");
        flashcardDTO.setDefinition("definition1");
    }

    @WithMockUser(authorities = "flashcard.read")
    @Test
    void getAllFlashcards() throws Exception {
        when(flashcardService.getFlashcardsByDeckId(anyLong()))
                .thenReturn(List.of(flashcardDTO, flashcardDTO));

        ResultActions response = mockMvc.perform(get(BASE_URL_FLASHCARDS + "/{deckId}/all", 2L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(2)));

        verify(flashcardService, times(1)).getFlashcardsByDeckId(anyLong());
    }

    @WithMockUser(authorities = "flashcard.read")
    @Test
    void getAllFlashcardsWhenDeckDoesNotExist() throws Exception {
        when(flashcardService.getFlashcardsByDeckId(2L))
                .thenThrow(NoSuchUserException.class);

        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL_FLASHCARDS + "/{deckId}/all", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(flashcardService, times(1)).getFlashcardsByDeckId(anyLong());
    }

    @WithMockUser(authorities = "flashcard.create")
    @Test
    void createFlashcard() throws Exception {
        when(flashcardService.createOrUpdate(anyLong(), any(FlashcardDTO.class)))
                .thenReturn(flashcardDTO);

        ResultActions resultActions = mockMvc.perform(post(BASE_URL_FLASHCARDS + "/{deckId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flashcardDTO)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.term", CoreMatchers.is(flashcardDTO.getTerm())))
                .andExpect(jsonPath("$.definition", CoreMatchers.is(flashcardDTO.getDefinition())));

        verify(flashcardService, times(1)).createOrUpdate(anyLong(), any(FlashcardDTO.class));
    }

    @WithMockUser(authorities = "flashcard.create")
    @Test
    void createFlashcardWhenDeckDoesNotExist() throws Exception {
        when(flashcardService.createOrUpdate(anyLong(), any(FlashcardDTO.class)))
                .thenThrow(NoSuchDeckException.class);

        MockHttpServletResponse response = mockMvc.perform(post(BASE_URL_FLASHCARDS + "/{deckId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flashcardDTO)))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(flashcardService, times(1)).createOrUpdate(anyLong(), any(FlashcardDTO.class));
    }


    @WithMockUser(authorities = "flashcard.update")
    @Test
    void updateFlashcard() throws Exception {
        when(flashcardService.createOrUpdate(anyLong(), any(FlashcardDTO.class)))
                .thenReturn(flashcardDTO);

        ResultActions resultActions = mockMvc.perform(put(BASE_URL_FLASHCARDS + "/{deckId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flashcardDTO)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.term", CoreMatchers.is(flashcardDTO.getTerm())))
                .andExpect(jsonPath("$.definition", CoreMatchers.is(flashcardDTO.getDefinition())));

        verify(flashcardService, times(1)).createOrUpdate(anyLong(), any(FlashcardDTO.class));
    }

    @WithMockUser(authorities = "flashcard.update")
    @Test
    void updateFlashcardWhenDeckDoesNotExist() throws Exception {
        when(flashcardService.createOrUpdate(anyLong(), any(FlashcardDTO.class)))
                .thenThrow(NoSuchDeckException.class);

        ResultActions resultActions = mockMvc.perform(put(BASE_URL_FLASHCARDS + "/{deckId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flashcardDTO)));

        resultActions.andExpect(status().isNotFound());

        verify(flashcardService, times(1)).createOrUpdate(anyLong(), any(FlashcardDTO.class));
    }

    @WithMockUser(authorities = "flashcard.delete")
    @Test
    void deleteFlashcard() throws Exception {
        doNothing().when(flashcardService).deleteById(anyLong());

        ResultActions resultActions = mockMvc.perform(delete(BASE_URL_FLASHCARDS + "/{flashcardId}      ", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());

        verify(flashcardService, times(1)).deleteById(anyLong());
    }

    @WithMockUser(authorities = "flashcard.read")
    @Test
    void getFlashcard() throws Exception {
        when(flashcardService.getOneById(anyLong()))
                .thenReturn(flashcardDTO);

        ResultActions resultActions = mockMvc.perform(get(BASE_URL_FLASHCARDS + "/{flashcardId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.term", CoreMatchers.is(flashcardDTO.getTerm())))
                .andExpect(jsonPath("$.definition", CoreMatchers.is(flashcardDTO.getDefinition())));

        verify(flashcardService, times(1)).getOneById(anyLong());
    }

    @WithMockUser(authorities = "flashcard.read")
    @Test
    void getFlashcardWhenFlashcardDoesNotExist() throws Exception {
        when(flashcardService.getOneById(anyLong()))
                .thenThrow(NoSuchFlashcardException.class);

        ResultActions resultActions = mockMvc.perform(get(BASE_URL_FLASHCARDS + "/{flashcardId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());

        verify(flashcardService, times(1)).getOneById(anyLong());
    }
}
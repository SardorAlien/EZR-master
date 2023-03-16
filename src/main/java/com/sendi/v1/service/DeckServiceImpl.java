package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.UserDTO;
import com.sendi.v1.dto.mapper.DeckMapper;
import com.sendi.v1.dto.mapper.UserMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepo;
    private final DeckMapper deckMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public List<DeckDTO> getDecksByUser(User user) {
        if (user == null) {
            return Collections.emptyList();
        }

        List<DeckDTO> deckDTOs = deckRepo.findAllByUser(user)
                .stream()
                .map(deckMapper::deckToDeckDTO)
                .collect(Collectors.toList());

        return deckDTOs;
    }

    @Override
    public List<DeckDTO> getDecksByUserId(Long userId) {
        UserDTO userDTO = userService.getUserById(userId);

        User user = userMapper.userDTOToUser(userDTO);

        return getDecksByUser(user);
    }

    @Override
    public DeckDTO getDeckById(Long id) {
        Optional<Deck> deckOptional = deckRepo.findDeckById(id);

        if (deckOptional.isEmpty()) {
            return null;
        }

        Deck deck = deckOptional.get();

        DeckDTO newDeckDTO = deckMapper.deckToDeckDTO(deck);

        return newDeckDTO;
    }

    @Override
    public DeckDTO createOrUpdateDeck(DeckDTO deckDTO) {
        Deck deck = deckMapper.deckDTOToDeck(deckDTO);

        deckRepo.save(deck);

        DeckDTO newDeckDTO = deckMapper.deckToDeckDTO(deck);

        return newDeckDTO;
    }

    @Override
    public void deleteById(Long id) {
        deckRepo.deleteById(id);
    }
}

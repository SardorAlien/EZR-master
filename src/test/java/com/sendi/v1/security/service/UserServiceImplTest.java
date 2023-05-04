package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.service.dto.UserDTO;
import com.sendi.v1.service.dto.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    void shouldCreateOrUpdate() {
        // Arrange
        User expected = User.builder()
                .firstname("Draco")
                .lastname("Malfoy")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(expected);

        // Act
        User actual = userService.createOrUpdate(new User());

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldGetUser() {
        // Arrange
        String username = "hpotter23";
        User expected = User.builder()
                .firstname("Harry")
                .lastname("Potter")
                .email("potter23@gmail.com")
                .password("123fa4f65af")
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(expected));

        // Act
        User actual = userService.getUser(username);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(userRepository, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldGetUserThrowsNoSuch() {
        lenient().when(userRepository.findByUsername(anyString())).thenThrow(NoSuchUserException.class);

        assertThrows(NoSuchUserException.class, () -> {
            userService.getUser("test");
        });

        verify(userRepository, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldGetAllUsers() {
        // Arrange
        List<User> list = List.of(new User(), new User());

        when(userRepository.findAll()).thenReturn(list);
        when(userMapper.toDTOs(anyList())).thenReturn(List.of(new UserDTO(), new UserDTO()));

        // Act & Assert
        assertThat(userService.getUsers()).hasSize(2);
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toDTOs(anyList());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void shouldGetUserById() {
        // Arrange
        User expected = User.builder()
                .firstname("Hermione")
                .lastname("Granger")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expected));

        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setFirstname("Hermione");
        expectedDTO.setLastname("Granger");
        when(userMapper.toDTO(any(User.class))).thenReturn(expectedDTO);

        // Act & Assert
        assertThat(userService.getUserById(getRandomLong())).usingRecursiveComparison().isEqualTo(expectedDTO);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userMapper, times(1)).toDTO(any(User.class));
    }

    @Test
    void shouldReturnCount() {
        long expected = getRandomLong();

        when(userRepository.count()).thenReturn(expected);

        assertThat(userService.count()).usingRecursiveComparison().isEqualTo(expected);
        verify(userRepository, times(1)).count();
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }
}
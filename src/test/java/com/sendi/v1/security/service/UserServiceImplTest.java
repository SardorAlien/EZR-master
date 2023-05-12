package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.exception.custom.UserDuplicationException;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.service.dto.UserDTO;
import com.sendi.v1.service.dto.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    private static final String NO_SUCH_USER_ID_EXC_MESSAGE = "No such user with id: ";

    @Test
    void shouldCreateOrUpdate() throws Exception {
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
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowCreateOrUpdate() throws Exception {
        // Arrange
        User user = User.builder()
                .firstname("Ron")
                .lastname("Weasley")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        User alreadySavedUser = userService.createOrUpdate(new User());

        when(userRepository.save(alreadySavedUser)).thenThrow(UserDuplicationException.class);

        // Act & Assert
        assertNotNull(alreadySavedUser);
        assertThrows(UserDuplicationException.class, () -> {
            userService.createOrUpdate(alreadySavedUser);
        });
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
        assertThat(actual).isEqualTo(expected);
        verify(userRepository).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowsGetUser() {
        when(userRepository.findByUsername(anyString())).thenThrow(NoSuchUserException.class);

        assertThrows(NoSuchUserException.class, () -> {
            userService.getUser("test");
        });
        verify(userRepository).findByUsername(anyString());
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
        verify(userRepository).findAll();
        verify(userMapper).toDTOs(anyList());
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
        verify(userRepository).findById(anyLong());
        verify(userMapper).toDTO(any(User.class));
    }

    @Test
    @Disabled
    void shouldThrowGetUserById() {
        when(userRepository.findById(anyLong())).thenThrow(new NoSuchUserException(NO_SUCH_USER_ID_EXC_MESSAGE));

        Exception exception = assertThrows(NoSuchUserException.class, () -> {
            userService.getUserById(getRandomLong());
        });

        String actualMessage = exception.getMessage();

        assertEquals(NO_SUCH_USER_ID_EXC_MESSAGE, actualMessage);
    }

    @Test
    void shouldReturnCount() {
        long expected = getRandomLong();

        when(userRepository.count()).thenReturn(expected);

        assertThat(userService.count()).usingRecursiveComparison().isEqualTo(expected);
        verify(userRepository).count();
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }
}
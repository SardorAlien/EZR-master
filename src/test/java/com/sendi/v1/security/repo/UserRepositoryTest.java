package com.sendi.v1.security.repo;

import com.sendi.v1.DefaultJpaTestConfiguration;
import com.sendi.v1.security.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = DefaultJpaTestConfiguration.class)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("Sardor23")
                .email("sardor23@gmail.com")
                .firstname("Sardor")
                .lastname("Juma")
                .password("123")
                .build();
    }

    @Test
    void findByUsername() {
        userRepository.save(user);

        User actualUser = userRepository.findByUsername("Sardor23").get();

        assertThat(actualUser).isNotNull();
    }

    @Test
    void findByEmail() {
        userRepository.save(user);

        User actualUser = userRepository.findByEmail("sardor23@gmail.com").get();

        assertThat(actualUser.getFirstname()).isEqualTo("Sardor");
    }

    @Test
    void existsByUsername() {
        userRepository.save(user);

        boolean doesExist = userRepository.existsByUsername("Sardor23");

        assertTrue(doesExist);
    }

    @Test
    void existsByUsernameDoesNotExist() {
        userRepository.save(user);

        boolean doesExist = userRepository.existsByUsername("Sardor231");

        assertFalse(doesExist);
    }
}
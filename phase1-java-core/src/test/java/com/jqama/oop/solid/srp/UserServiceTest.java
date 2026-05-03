package com.jqama.oop.solid.srp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for UserService (SRP).
 */
@ExtendWith(MockitoExtension.class) // enables Mockito annotations in JUnit 5
@DisplayName("SRP — UserService")
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // fake — no real DB

    @Mock
    private EmailService emailService; // fake — no real emails sent

    @InjectMocks
    private UserService userService; // real class under test

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "Junaid Qamar", "jj@example.com");
    }

    // ── registerUser ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("registerUser should save user to repository")
    void registerUser_shouldSaveToRepository() {
        userService.registerUser(testUser);

        // verify: was save() called exactly once with our user?
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @DisplayName("registerUser should send a welcome email")
    void registerUser_shouldSendWelcomeEmail() {
        userService.registerUser(testUser);

        verify(emailService, times(1)).sendWelcome(testUser);
    }

    @Test
    @DisplayName("registerUser should save THEN email — order matters in real systems")
    void registerUser_shouldSaveBeforeEmail() {
        // inOrder ensures save() is called before sendWelcome()
        // Real-world reason: if save fails, we don't want to send a welcome email
        var inOrder = inOrder(userRepository, emailService);

        userService.registerUser(testUser);

        inOrder.verify(userRepository).save(testUser);
        inOrder.verify(emailService).sendWelcome(testUser);
    }

    // ── findUser ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findUser should return user when found in repository")
    void findUser_shouldReturnUser_whenFound() {
        // stub: when findById(1L) is called on the mock, return our user
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findUser(1L);

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("Junaid Qamar");
        assertThat(result.get().email()).isEqualTo("jj@example.com");
    }

    @Test
    @DisplayName("findUser should return empty Optional when user does not exist")
    void findUser_shouldReturnEmpty_whenNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUser(999L);

        assertThat(result).isEmpty();
    }

    // ── getAllUsers ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("getAllUsers should return all users from repository")
    void getAllUsers_shouldReturnAllUsers() {
        User user2 = new User(2L, "Alice", "alice@example.com");
        when(userRepository.findAll()).thenReturn(List.of(testUser, user2));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(2)
                .extracting(User::name)
                .containsExactlyInAnyOrder("Junaid Qamar", "Alice");
    }

    // ── removeUser ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("removeUser should delegate to repository deleteById")
    void removeUser_shouldCallDeleteOnRepository() {
        userService.removeUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("removeUser should NOT trigger any email")
    void removeUser_shouldNotSendEmail() {
        userService.removeUser(1L);

        // verifyNoInteractions: confirms the mock was never touched at all
        verifyNoInteractions(emailService);
    }
}
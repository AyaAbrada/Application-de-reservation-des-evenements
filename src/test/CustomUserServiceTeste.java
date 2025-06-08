package com.example.reservation_des_evenements.Services;
import com.example.reservation_des_evenements.Repositories.UserRepositorie;
import com.example.reservation_des_evenements.entities.Role;
import com.example.reservation_des_evenements.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailServiceTest {

    private UserRepositorie userRepositorie;
    private CustomUserDetailService customUserDetailService;

    @BeforeEach
    void setUp() {
        userRepositorie = mock(UserRepositorie.class);
        customUserDetailService = new CustomUserDetailService(userRepositorie);
    }

    @Test
    void testLoadUserByUsername_userExists() {

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("password123");
        mockUser.setRole(Role.USER);

        when(userRepositorie.findByUsername("testuser")).thenReturn(mockUser);

        UserDetails userDetails = customUserDetailService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(
                auth -> auth.getAuthority().equals("USER")));
    }

    @Test
    void testLoadUserByUsername_userNotFound() {
        when(userRepositorie.findByUsername("unknown")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailService.loadUserByUsername("unknown");
        });
    }
}

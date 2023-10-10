package com.tennisWebsite.service;

import com.tennisWebsite.dao.RoleRepo;
import com.tennisWebsite.dao.UserRepo;
import com.tennisWebsite.dto.RegistrationDto;
import com.tennisWebsite.entity.Role;
import com.tennisWebsite.entity.UserEntity;
import com.tennisWebsite.service.Impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    private UserEntity user;
    private RegistrationDto userDto;
    private Role role;

    @BeforeEach
    private void init() {
        user = UserEntity.builder()
                .username("test")
                .email("test@gmail.com")
                .password("test")
                .build();
        userDto = RegistrationDto.builder()
                .username("test")
                .email("test@gmail.com")
                .password("test")
                .build();
        role = Role.builder()
                .name("USER")
                .build();
    }

    @Test
    public void UserService_SaveUser_ReturnsUser() {
        when(roleRepo.findByName("USER")).thenReturn(role);
        when(userRepo.save(Mockito.any(UserEntity.class))).thenReturn(user);

        UserEntity savedUser = userService.saveUser(userDto);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void UserService_findByEmail_ReturnsEmail() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        UserEntity returnedUser = userService.findByEmail(user.getEmail());

        Assertions.assertThat(returnedUser).isNotNull();
        Assertions.assertThat(returnedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void UserService_findByUsername_ReturnsUsername() {
        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);

        UserEntity returnedUser = userService.findByUsername(user.getUsername());

        Assertions.assertThat(returnedUser).isNotNull();
        Assertions.assertThat(returnedUser.getUsername()).isEqualTo(user.getUsername());
    }
}

package com.tennisWebsite.service;

import com.tennisWebsite.dto.RegistrationDto;
import com.tennisWebsite.entity.UserEntity;

public interface UserService {
    UserEntity saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}

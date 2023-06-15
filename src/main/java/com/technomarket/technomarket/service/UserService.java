package com.technomarket.technomarket.service;

import com.technomarket.technomarket.dto.users.UserDto;
import com.technomarket.technomarket.dto.users.UserSummaryDto;
import com.technomarket.technomarket.dto.users.auth.UserRegistrationDto;
import com.technomarket.technomarket.entity.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void register(UserRegistrationDto registrationDto);

    List<UserSummaryDto> getAllUsersSummaryDto();

    List<User> findAllUsers();

    User findUserByUsername(String username);

    User findUserByPrincipal(Principal principal);

    UserDto getUserDtoById(Long userId);

    User findUserById(Long userId);

    Principal getPrincipal();

    void deleteUserById(Long userId);
}

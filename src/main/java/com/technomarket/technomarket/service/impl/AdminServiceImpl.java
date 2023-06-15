package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.dto.admin.AdminUserDto;
import com.technomarket.technomarket.dto.admin.AdminUserSummaryDto;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.service.AdminService;
import com.technomarket.technomarket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{
    private final UserService userService;

    @Autowired
    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AdminUserDto getUserDtoById(Long userId) {
        User user = userService.findUserById(userId);
        return AdminUserDto.fromUser(user);
    }

    @Override
    public List<AdminUserSummaryDto> getAllAdminUsersSummaryDto() {
        List<User> users = userService.findAllUsers();
        return users.stream()
                .map(AdminUserSummaryDto::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long userId) {
        userService.deleteUserById(userId);
    }

}

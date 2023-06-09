package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.users.UserDto;
import com.technomarket.technomarket.dto.users.UserSummaryDto;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDto>> getUsers(){
        List<UserSummaryDto> users = userService.getAllUsersSummaryDto();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        UserDto user = userService.getUserDtoById(id);
        return ResponseEntity.ok(user);
    }


}

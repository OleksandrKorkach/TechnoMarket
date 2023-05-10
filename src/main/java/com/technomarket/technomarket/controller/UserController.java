package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.UserDto;
import com.technomarket.technomarket.dto.UserRegistrationDto;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(){
        List<User> usersFromDb = userService.getAll();
        List<UserDto> users = usersFromDb.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        User user = userService.findById(id);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto userDto = UserDto.fromUser(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto registrationDto){
        User user = registrationDto.toUser();
        userService.register(user);
        return ResponseEntity.ok().body("Successfully created!");
    }
}

package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.AdminUserDto;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable Long id){
        User user = userService.findById(id);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto adminUserDto = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(adminUserDto, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDto>> getUsers(){
        List<User> usersFromDb = userService.getAll();
        List<AdminUserDto> users = usersFromDb.stream()
                .map(AdminUserDto::fromUser)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/{id}/delete")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }


}

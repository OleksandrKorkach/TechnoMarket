package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.admin.AdminUserDto;
import com.technomarket.technomarket.dto.admin.AdminUserSummaryDto;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/users/user{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable Long id){
        User user = userService.findById(id);
        AdminUserDto adminUserDto = AdminUserDto.fromUser(user);
        return ResponseEntity.ok(adminUserDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserSummaryDto>> getUsers(){
        List<User> usersFromDb = userService.getAll();
        List<AdminUserSummaryDto> users = usersFromDb.stream()
                .map(AdminUserSummaryDto::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/user{id}/delete")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok("Successfully deleted");
    }


}

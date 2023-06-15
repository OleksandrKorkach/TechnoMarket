package com.technomarket.technomarket.controller;

import com.technomarket.technomarket.dto.admin.AdminUserDto;
import com.technomarket.technomarket.dto.admin.AdminUserSummaryDto;
import com.technomarket.technomarket.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users/user{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable Long id){
        AdminUserDto user = adminService.getUserDtoById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserSummaryDto>> getAllUsers(){
        List<AdminUserSummaryDto> users = adminService.getAllAdminUsersSummaryDto();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/user{id}/delete")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        adminService.deleteUserById(id);
        return ResponseEntity.ok("Successfully deleted user with id: " + id);
    }


}

package com.technomarket.technomarket.dto.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.entity.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserSummaryDto {
    private Long id;
    private String username;
    private String email;
    private String status;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));
        return user;
    }

    public static AdminUserSummaryDto fromUser(User user) {
        AdminUserSummaryDto dto = new AdminUserSummaryDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus().name());
        return dto;
    }
}

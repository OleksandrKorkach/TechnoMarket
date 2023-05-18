package com.technomarket.technomarket.dto.users.auth;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}

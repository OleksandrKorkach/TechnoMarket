package com.technomarket.technomarket.dto.users;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}

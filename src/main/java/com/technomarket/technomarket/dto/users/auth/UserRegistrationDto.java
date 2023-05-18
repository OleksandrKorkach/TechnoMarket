package com.technomarket.technomarket.dto.users.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationDto {
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}

package com.technomarket.technomarket.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProductOwnerDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;

    public static UserProductOwnerDto fromUser(User user){
        UserProductOwnerDto dto = new UserProductOwnerDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}

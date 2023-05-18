package com.technomarket.technomarket.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.technomarket.technomarket.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSummaryDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime created;

    public static UserSummaryDto fromUser(User user){
        UserSummaryDto dto = new UserSummaryDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCreated(user.getCreated());
        return dto;
    }

}

package com.technomarket.technomarket.service;

import com.technomarket.technomarket.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void deleteById(Long id);
}

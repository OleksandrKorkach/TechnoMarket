package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.entity.Role;
import com.technomarket.technomarket.entity.User;
import com.technomarket.technomarket.entity.enums.Status;
import com.technomarket.technomarket.repository.RoleRepository;
import com.technomarket.technomarket.repository.UserRepository;
import com.technomarket.technomarket.service.UserService;
import com.technomarket.technomarket.service.impl.exceptions.ResourceNotFoundException;
import com.technomarket.technomarket.service.impl.exceptions.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        User registeredUser = userRepository.save(user);

        log.info("IN registration - user: {} successfully registered", registeredUser);
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()){
            throw new ResourceNotFoundException("No users in db");
        }
        log.info("IN getAll - users: {} found", allUsers);
        return allUsers;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedAccessException("Unauthorized request, please login first");
        }
        return userRepository.findByUsername(principal.getName());
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username {}", user, username);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        log.info("IN findById - user: {} found by id ", user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            log.info("successfully deleted user with id {}", id);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

}

package com.technomarket.technomarket.service.impl;

import com.technomarket.technomarket.dto.users.UserDto;
import com.technomarket.technomarket.dto.users.UserSummaryDto;
import com.technomarket.technomarket.dto.users.auth.UserRegistrationDto;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void register(UserRegistrationDto registrationDto) {
        User user = registrationDto.toUser();
        setUserRoles(user);
        setRegistrationDetails(user);
        User registeredUser = userRepository.save(user);
        log.info("IN registration - user: {} successfully registered", registeredUser);
    }

    public void setUserRoles(User user){
        Role userRole = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
    }

    public void setRegistrationDetails(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
    }

    @Override
    public List<UserSummaryDto> getAllUsersSummaryDto(){
        List<User> users = findAllUsers();
        return users.stream()
                .map(UserSummaryDto::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()){
            throw new ResourceNotFoundException("No users in db");
        }
        log.info("IN getAll - users: {} found", allUsers);
        return allUsers;
    }

    @Override
    public Principal getPrincipal(){
       return SecurityContextHolder.getContext().getAuthentication();
    }

    public User findUserByPrincipal(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedAccessException("Unauthorized request, please login first");
        }
        return userRepository.findByUsername(principal.getName());
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        log.info("IN findByUsername - user: {} found by username {}", user, username);
        return user;
    }

    @Override
    public UserDto getUserDtoById(Long id){
        User user = findUserById(id);
        return UserDto.fromUser(user);
    }

    @Override
    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        log.info("IN findById - user: {} found by id ", user);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            log.info("successfully deleted user with id {}", id);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

}

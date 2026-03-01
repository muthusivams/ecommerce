package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserDtos;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtos.UserResponse register(@Valid @RequestBody UserDtos.RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/auth/login")
    public UserDtos.AuthResponse login(@Valid @RequestBody UserDtos.LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/{id}")
    public UserDtos.UserResponse getProfile(@PathVariable Long id) {
        return userService.getProfile(id);
    }
}

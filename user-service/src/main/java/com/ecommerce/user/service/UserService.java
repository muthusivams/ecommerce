package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserDtos;
import com.ecommerce.user.entity.Role;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.NotFoundException;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper mapper;

    public UserDtos.UserResponse register(UserDtos.RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        return mapper.map(userRepository.save(user), UserDtos.UserResponse.class);
    }

    public UserDtos.AuthResponse login(UserDtos.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        UserDtos.AuthResponse response = new UserDtos.AuthResponse();
        response.setToken(jwtService.generate(user.getEmail(), user.getRole().name()));
        return response;
    }

    public UserDtos.UserResponse getProfile(Long id) {
        return mapper.map(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Profile not found")), UserDtos.UserResponse.class);
    }
}

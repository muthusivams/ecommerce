package com.ecommerce.user.dto;

import com.ecommerce.user.entity.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDtos {
    @Data
    public static class RegisterRequest {
        @Email private String email;
        @NotBlank private String password;
        @NotBlank private String fullName;
    }

    @Data
    public static class LoginRequest {
        @Email private String email;
        @NotBlank private String password;
    }

    @Data
    public static class UserResponse {
        private Long id;
        private String email;
        private String fullName;
        private Role role;
    }

    @Data
    public static class AuthResponse {
        private String token;
    }
}

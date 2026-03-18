package com.maz.academy.user;

import com.maz.academy.core.enums.UserRole;

public record UserResponseDTO(
        String id,
        String name,
        String email,
        UserRole role
) {
    public static UserResponseDTO fromEntity(User user, String prefix){
        return new UserResponseDTO(
                prefix + user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getUserRole()
        );
    }
}

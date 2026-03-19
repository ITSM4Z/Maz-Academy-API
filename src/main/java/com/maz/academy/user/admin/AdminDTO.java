package com.maz.academy.user.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AdminDTO(
        @NotEmpty
        String name,
        @NotEmpty
        @Email
        String email
) {
}

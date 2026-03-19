package com.maz.academy.user.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record StudentDTO(
        @NotEmpty(message = "Full name should not be empty.")
        String name,
        @NotEmpty(message = "Email should not be empty.")
        @Email
        String email,
        @NotEmpty(message = "Major should not be empty.")
        String major
) {

}

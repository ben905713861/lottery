package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInDto {
    @NotEmpty
    @Size(max = 20)
    private String name;

    @NotEmpty
    private String icon;
}

package com.ecommerce.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class AuthenticationResponseDto {
    private String jwt;

    public AuthenticationResponseDto(String jwt) {
        this.jwt = jwt;
    }
}
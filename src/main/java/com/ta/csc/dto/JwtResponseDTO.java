package com.ta.csc.dto;

public class JwtResponseDTO {
    private final String token;

    public JwtResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}

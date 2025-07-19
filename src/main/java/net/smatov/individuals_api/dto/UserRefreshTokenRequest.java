package net.smatov.individuals_api.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRefreshTokenRequest {

    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}

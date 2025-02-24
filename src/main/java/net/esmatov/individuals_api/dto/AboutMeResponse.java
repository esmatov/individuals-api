package net.esmatov.individuals_api.dto;

import java.util.List;

public class AboutMeResponse {

    private String userId;
    private String username;
    private String name;
    private String email;
    private List<String> roles;

    public AboutMeResponse(String userId, String username, String name, String email, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}

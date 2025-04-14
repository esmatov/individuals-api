package net.esmatov.individuals_api.dto;

import jakarta.validation.constraints.*;

public class UserRegistrationRequest {

    @NotEmpty
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @NotEmpty
    private String email;
    @Size(min = 6)
    @NotEmpty
    private String password;
    @Size(min = 6)
    @NotEmpty
    private String confirmPassword;

    public UserRegistrationRequest(String username, String firstName, String lastName,
                                   String email, String password, String confirmPassword) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}

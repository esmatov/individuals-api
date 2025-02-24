package net.esmatov.individuals_api.client.keycloak_model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserRepresentation {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean emailVerified;
    private boolean enabled;
    private List<CredentialRepresentation> credentials;
    private List<String> realmRoles;
    private Map<String, List<String>> clientRoles;
    private LocalDateTime createdTimestamp;

    public UserRepresentation(UUID id, String username, String firstName,
                              String lastName, String email, boolean emailVerified,
                              boolean enabled, List<CredentialRepresentation> credentials, List<String> realmRoles,
                              Map<String, List<String>> clientRoles, LocalDateTime createdTimestamp) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.emailVerified = emailVerified;
        this.enabled = enabled;
        this.credentials = credentials;
        this.realmRoles = realmRoles;
        this.clientRoles = clientRoles;
        this.createdTimestamp = createdTimestamp;
    }

    public UserRepresentation(UserRepresentation other) {
        this.id = other.getId();
        this.username = other.getUsername();
        this.firstName = other.getFirstName();
        this.lastName = other.getLastName();
        this.email = other.getEmail();
        this.emailVerified = other.isEmailVerified();
        this.enabled = other.isEnabled();
        this.credentials = other.getCredentials();
        this.realmRoles = other.getRealmRoles();
        this.clientRoles = other.getClientRoles();
        this.createdTimestamp = other.getCreatedTimestamp();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<CredentialRepresentation> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<CredentialRepresentation> credentials) {
        this.credentials = credentials;
    }

    public List<String> getRealmRoles() {
        return realmRoles;
    }

    public void setRealmRoles(List<String> realmRoles) {
        this.realmRoles = realmRoles;
    }

    public Map<String, List<String>> getClientRoles() {
        return clientRoles;
    }

    public void setClientRoles(Map<String, List<String>> clientRoles) {
        this.clientRoles = clientRoles;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public void addPassword(String password, boolean temporary) {
        this.credentials = List.of(new CredentialRepresentation("password", password, temporary));
    }

    public static class CredentialRepresentation {
        private String type;
        private String value;
        private boolean temporary;

        public CredentialRepresentation(String type, String value, boolean temporary) {
            this.type = type;
            this.value = value;
            this.temporary = temporary;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isTemporary() {
            return temporary;
        }

        public void setTemporary(boolean temporary) {
            this.temporary = temporary;
        }
    }

}

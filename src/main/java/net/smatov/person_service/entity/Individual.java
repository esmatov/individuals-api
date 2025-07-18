package net.smatov.person_service.entity;

import java.time.Instant;
import java.util.UUID;

public class Individual {

    private UUID uuid;
    private User user;
    private String passportNumber;
    private String phoneNumber;
    private String email;
    private Instant verifiedAt;
    private Instant archivedAt;
    private String status;

    private Individual(Builder builder) {
        this.uuid = builder.uuid;
        this.user = builder.user;
        this.passportNumber = builder.passportNumber;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.verifiedAt = builder.verifiedAt;
        this.archivedAt = builder.archivedAt;
        this.status = builder.status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public Instant getArchivedAt() {
        return archivedAt;
    }

    public String getStatus() {
        return status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID uuid;
        private User user;
        private String passportNumber;
        private String phoneNumber;
        private String email;
        private Instant verifiedAt;
        private Instant archivedAt;
        private String status;

        public Builder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder passportNumber(String passportNumber) {
            this.passportNumber = passportNumber;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder verifiedAt(Instant verifiedAt) {
            this.verifiedAt = verifiedAt;
            return this;
        }

        public Builder archivedAt(Instant archivedAt) {
            this.archivedAt = archivedAt;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Individual build() {
            return new Individual(this);
        }
    }

}

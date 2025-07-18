package net.smatov.person_service.entity;

import java.time.Instant;
import java.util.UUID;

public class User {

    private UUID uuid;
    private Address address;
    private String secretKey;
    private String firstName;
    private String lastName;
    private Instant created;
    private Instant updated;
    private Boolean filled;

    private User(User.Builder builder) {
        this.uuid = builder.uuid;
        this.address = builder.address;
        this.secretKey = builder.secretKey;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.created = builder.created;
        this.updated = builder.updated;
        this.filled = builder.filled;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID uuid;
        private Address address;
        private String secretKey;
        private String firstName;
        private String lastName;
        private Instant created;
        private Instant updated;
        private Boolean filled;

        public Builder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder updated(Instant updated) {
            this.updated = updated;
            return this;
        }

        public Builder filled(Boolean filled) {
            this.filled = filled;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}

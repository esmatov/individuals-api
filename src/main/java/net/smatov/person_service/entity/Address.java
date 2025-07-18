package net.smatov.person_service.entity;

import java.time.Instant;
import java.util.UUID;

public class Address {

    private UUID uuid;
    private Country country;
    private Instant created;
    private Instant updated;
    private String address;
    private String zipCode;
    private String city;
    private String state;
    private Boolean archived;

    private Address(Address.Builder builder) {
        this.uuid = builder.uuid;
        this.country = builder.country;
        this.created = builder.created;
        this.updated = builder.updated;
        this.address = builder.address;
        this.zipCode = builder.zipCode;
        this.city = builder.city;
        this.state = builder.state;
        this.archived = builder.archived;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID uuid;
        private Country country;
        private Instant created;
        private Instant updated;
        private String address;
        private String zipCode;
        private String city;
        private String state;
        private Boolean archived;

        public Builder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder country(Country country) {
            this.country = country;
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

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder archived(Boolean archived) {
            this.archived = archived;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

}

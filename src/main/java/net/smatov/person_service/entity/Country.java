package net.smatov.person_service.entity;

import java.time.Instant;

public class Country {

    private Integer id;
    private Instant created;
    private Instant updated;
    private String name;
    private String alpha2;
    private String alpha3;
    private String status;

    public Country(Country.Builder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.name = builder.name;
        this.alpha2 = builder.alpha2;
        this.alpha3 = builder.alpha3;
        this.status = builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Instant created;
        private Instant updated;
        private String name;
        private String alpha2;
        private String alpha3;
        private String status;

        public Builder id(Integer id) {
            this.id = id;
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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder alpha2(String alpha2) {
            this.alpha2 = alpha2;
            return this;
        }

        public Builder alpha3(String alpha3) {
            this.alpha3 = alpha3;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Country build() {
            return new Country(this);
        }
    }

}

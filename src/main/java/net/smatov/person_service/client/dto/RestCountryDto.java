package net.smatov.person_service.client.dto;

public class RestCountryDto {
    private final Name name;
    private final String cca2;
    private final String cca3;

    public RestCountryDto(Name name, String cca2, String cca3) {
        this.name = name;
        this.cca2 = cca2;
        this.cca3 = cca3;
    }

    public Name getName() {
        return name;
    }

    public String getCca2() {
        return cca2;
    }

    public String getCca3() {
        return cca3;
    }

    public class Name {
        private final String common;
        private final String official;

        public Name(String common, String official) {
            this.common = common;
            this.official = official;
        }

        public String getCommon() {
            return common;
        }

        public String getOfficial() {
            return official;
        }
    }

}

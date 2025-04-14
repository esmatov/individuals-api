package net.esmatov.individuals_api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfigurationProperties {
    private final String server;
    private final String port;
    private final String realm;
    private final Auth auth;

    public KeycloakConfigurationProperties(String server, String port, String realm, Auth auth) {
        this.server = server;
        this.port = port;
        this.realm = realm;
        this.auth = auth;
    }

    public String getServer() {
        return server;
    }

    public String getPort() {
        return port;
    }

    public String getRealm() {
        return realm;
    }

    public String getAuthClientId() {
        return auth.clientId;
    }

    public String getAuthClientSecret() {
        return auth.clientSecret;
    }


    public static class Auth {
        private final String clientId;
        private final String clientSecret;

        public Auth(String clientId, String clientSecret) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }
    }

}

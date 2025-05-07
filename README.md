# Individuals-API

Lightweight Java project for learning purposes, built with modern Spring technologies.
It showcases the use of Spring Boot, WebFlux, Spring Security with OAuth2, Keycloak for identity and access management, and Testcontainers for integration testing.
Ideal for experimenting with reactive programming, secure API development, and containerized test environments.

## Running Locally

1. Make sure Docker and Keycloak are running.
2. Import the realm file into Keycloak.
3. Export the required environment variables.
4. Build and run the application.

To run this project, make sure you have a working **Keycloak** instance.

A pre-configured realm file (`realm-export-microservices.json`) is available in the `resources/` directory.  
It includes the `individuals-api` client with OAuth2 settings.

### Required Environment Variables

Before running the application, define the following environment variables:

| Variable              | Description                                | Example Value                                  |
|-----------------------|--------------------------------------------|------------------------------------------------|
| `INDIVIDUALS_API_PORT`| Port where the API will run                | `8080`                                         |
| `KC_CLIENT_ID`        | Keycloak client ID                         | `individuals-api`                              |
| `KC_CLIENT_SECRET`    | Keycloak client secret                     | `54wCiwxzPESBMixFk8fajFz8sZkSSL6b`             |
| `KC_PORT`             | Keycloak port                              | `8081`                                         |
| `KC_REALM`            | Name of the realm                          | `microservices`                                |
| `KC_SERVER`           | URL of the Keycloak server                 | `http://localhost:8081`                        |

## Authors
Smatov Erlan

## Version History

* 1.0.0
    * Initial Release

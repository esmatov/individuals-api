package net.smatov.person_service;

import org.springframework.boot.SpringApplication;

public class TestPersonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(PersonServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

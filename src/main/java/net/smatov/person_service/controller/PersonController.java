package net.smatov.person_service.controller;

import net.smatov.dto.PersonDto;
import net.smatov.person_service.entity.Individual;
import net.smatov.person_service.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/v1/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
        Individual individual = personService.registrationPerson(personDto);
        return ResponseEntity
                .created(URI.create("/v1/api/persons/" + individual.getUuid()))
                .contentType(APPLICATION_JSON)
                .build();
    }

    public ResponseEntity<?> removePerson(@RequestBody PersonDto personDto) {

    }

}

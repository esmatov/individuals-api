package net.smatov.person_service.service;

import net.smatov.dto.PersonDto;
import net.smatov.person_service.client.component.CountriesClientComponent;
import net.smatov.person_service.client.dto.RestCountryDto;
import net.smatov.person_service.entity.Country;
import net.smatov.person_service.entity.Individual;
import net.smatov.person_service.exception.IndividualAlreadyExistsException;
import net.smatov.person_service.repository.CountryRepository;
import net.smatov.person_service.repository.IndividualRepository;
import net.smatov.person_service.utils.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.smatov.person_service.factory.IndividualFactory.createIndividualWithCountryFromPersonDto;

@Service
public class PersonService {

    private final CountryRepository countryRepository;
    private final IndividualRepository individualRepository;
    private final CountriesClientComponent countriesClientComponent;

    public PersonService(CountryRepository countryRepository, IndividualRepository individualRepository,
                         CountriesClientComponent countriesClientComponent) {
        this.countryRepository = countryRepository;
        this.individualRepository = individualRepository;
        this.countriesClientComponent = countriesClientComponent;
    }

    @Transactional
    public Individual registrationPerson(PersonDto personDto) {
        if (individualRepository.existsIndividualByPassportNumberOrPhoneNumberOrEmail(
                personDto.getPassportNumber(), personDto.getPhoneNumber(), personDto.getEmail())) {
            throw new IndividualAlreadyExistsException();
        }
        Country countryEntity = checkInfoAndGetMoreInfoAboutCountry(personDto.getCountry());
        return individualRepository.save(
                createIndividualWithCountryFromPersonDto(personDto, countryEntity)
        );
    }

    private Country checkInfoAndGetMoreInfoAboutCountry(String countryName) {
        if (countryRepository.existsByNameIgnoreCase(countryName)) {
            return countryRepository.getByNameIgnoreCase(countryName);
        } else {
            RestCountryDto restCountry = countriesClientComponent.findMoreInfoAboutCountryByName(countryName);
            return countryRepository.save(Mapper.toCountryEntity(restCountry));
        }
    }

}

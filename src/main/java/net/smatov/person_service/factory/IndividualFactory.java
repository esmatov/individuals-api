package net.smatov.person_service.factory;

import net.smatov.dto.PersonDto;
import net.smatov.person_service.entity.Address;
import net.smatov.person_service.entity.Country;
import net.smatov.person_service.entity.Individual;
import net.smatov.person_service.entity.User;
import net.smatov.person_service.enumiration.IndividualStatus;

public class IndividualFactory {

    public static Individual createIndividualFromPersonDto(PersonDto personDto) {
        Country countryEntity = Country.builder()
                .name(personDto.getCountry())
                .build();

        Address addressEntity = Address.builder()
                .country(countryEntity)
                .address(personDto.getAddress())
                .zipCode(personDto.getZipCode())
                .city(personDto.getCity())
                .state(personDto.getState())
                .build();

        User userEntity = User.builder()
                .address(addressEntity)
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .build();

        return Individual.builder()
                .user(userEntity)
                .passportNumber(personDto.getPassportNumber())
                .phoneNumber(personDto.getPhoneNumber())
                .email(personDto.getEmail())
                .status(String.valueOf(IndividualStatus.ACTIVE))
                .build();
    }

    public static Individual createIndividualWithCountryFromPersonDto(PersonDto personDto, Country countryEntity) {
        Address addressEntity = Address.builder()
                .country(countryEntity)
                .address(personDto.getAddress())
                .zipCode(personDto.getZipCode())
                .city(personDto.getCity())
                .state(personDto.getState())
                .build();

        User userEntity = User.builder()
                .address(addressEntity)
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .build();

        return Individual.builder()
                .user(userEntity)
                .passportNumber(personDto.getPassportNumber())
                .phoneNumber(personDto.getPhoneNumber())
                .email(personDto.getEmail())
                .status(String.valueOf(IndividualStatus.ACTIVE))
                .build();
    }

}

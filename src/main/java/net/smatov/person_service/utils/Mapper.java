package net.smatov.person_service.utils;

import net.smatov.person_service.client.dto.RestCountryDto;
import net.smatov.person_service.entity.Country;

public class Mapper {

    public static Country toCountryEntity(RestCountryDto restCountryDto) {
        return Country.builder()
                .name(restCountryDto.getName().getCommon())
                .alpha2(restCountryDto.getCca2())
                .alpha3(restCountryDto.getCca3())
                .build();
    }

}

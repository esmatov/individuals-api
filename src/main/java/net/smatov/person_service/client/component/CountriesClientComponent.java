package net.smatov.person_service.client.component;

import net.smatov.person_service.client.dto.RestCountryDto;
import net.smatov.person_service.client.exception.CountryNotFoundException;
import net.smatov.person_service.client.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import static java.util.Optional.ofNullable;

@Component
public class CountriesClientComponent {

    private final RestTemplate restCountriesClient;

    public CountriesClientComponent(@Qualifier("restCountriesClient") RestTemplate restCountriesClient) {
        this.restCountriesClient = restCountriesClient;
    }

    public RestCountryDto findMoreInfoAboutCountryByName(String countryName) throws CountryNotFoundException, ServiceUnavailableException {
        try {
            return ofNullable(restCountriesClient
                    .getForEntity("/name/" + countryName, RestCountryDto[].class)
                    .getBody())
                    .map(countries -> countries[0])
                    .orElseThrow(CountryNotFoundException::new);
        } catch (HttpServerErrorException | ResourceAccessException serverException) {
            throw new ServiceUnavailableException();
        }
    }

}

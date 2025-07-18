package net.smatov.person_service.repository;

import net.smatov.person_service.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

    public boolean existsByName(String name);

    public boolean existsByNameIgnoreCase(String name);

    Country getByNameIgnoreCase(String name);
}

package net.smatov.person_service.repository;

import net.smatov.person_service.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, UUID> {

    public boolean existsIndividualByPassportNumberOrPhoneNumberOrEmail(
            String passportNumber, String phoneNumber, String email
    );

}

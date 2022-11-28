package com.ingsoftware.contactmanager.repositories;

import com.ingsoftware.contactmanager.domain.entitys.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    void deleteContactById(Long id);
    List<Contact> findAll();
    Optional<Contact> findByIdentifier(UUID id);

}

package com.ingsoftware.contactmanager.repositories;

import com.ingsoftware.contactmanager.domain.entitys.Contact;
import com.ingsoftware.contactmanager.domain.entitys.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAll();

    Optional<Contact> findContactByGuid(UUID id);

    Page<Contact> findAll(Pageable pageable);

    Page<Contact> findAllByUser(User user, Pageable pageable);

    Page<Contact> findAllByUserAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(User user, String name, String name1, Pageable pageable);
}

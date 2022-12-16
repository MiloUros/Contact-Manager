package com.ingsoftware.contactmanager.repositories;

import com.ingsoftware.contactmanager.domain.entitys.ContactType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

    List<ContactType> findAll();

    Optional<ContactType> findContactTypeByGuid(UUID id);

    boolean existsByValueIgnoreCase(String value);

    boolean existsByGuid(UUID id);

    void deleteByGuid(UUID id);

    @Override
    Page<ContactType> findAll(Pageable pageable);
}

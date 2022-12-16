package com.ingsoftware.contactmanager.repositories;

import com.ingsoftware.contactmanager.domain.entitys.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findUserByGuid(UUID id);

    Boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);
}

package com.ingsoftware.contactmanager.domain.entitys;

import com.ingsoftware.contactmanager.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Generated(GenerationTime.INSERT)
    @Column(unique = true)
    private UUID guid;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "first_name")
    @Size(max = 20, message = "Must not exceed 20 characters")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 20, message = "Must not exceed 20 characters")
    private String lastName;

    @Column(unique = true)
    @NotBlank
    @Size(max = 40, message = "Email must not exceed 40 characters.")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email format.")
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Contact> usersContacts = new ArrayList<>();

}

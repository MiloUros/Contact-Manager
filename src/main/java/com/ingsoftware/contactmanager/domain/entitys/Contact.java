package com.ingsoftware.contactmanager.domain.entitys;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contacts")
@Builder(toBuilder = true)
public class Contact {

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
    private String email;

    @Column(name = "phone_number")
    @Size(max = 20, message = "Must not exceed 20 characters")
    private String phoneNumber;

    @Size(max = 100, message = "Must not exceed 100 characters")
    private String address;

    @Size(max = 100, message = "Must not exceed 100 characters")
    private String info;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contact_type_id")
    private ContactType contactType;

    @Size(max = 25, message = "Must not exceed 25 characters")
    private String type;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    @NotNull
    private User user;

}

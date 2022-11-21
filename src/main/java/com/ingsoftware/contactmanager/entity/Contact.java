package com.ingsoftware.contactmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseEntity {

    private String first_Name;
    private String last_Name;
    private String email;
    private String password;
    private String phoneNumber;
    private ContactType contactType;
}

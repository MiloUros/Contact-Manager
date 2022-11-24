package com.ingsoftware.contactmanager.domain.entity;


import com.ingsoftware.contactmanager.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "contactType")
    private List<Contact> contactsPerContactType = new ArrayList<>();

    private Type contactType;
}

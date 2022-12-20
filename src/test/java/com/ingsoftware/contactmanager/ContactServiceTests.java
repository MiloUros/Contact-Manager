package com.ingsoftware.contactmanager;

import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.UpdateContactRequestDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import com.ingsoftware.contactmanager.domain.entitys.ContactType;
import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.mappers.ContactMapper;
import com.ingsoftware.contactmanager.exceptions.ActionNotAllowedException;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import com.ingsoftware.contactmanager.repositories.ContactTypeRepository;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import com.ingsoftware.contactmanager.services.ContactService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

    @Mock
    private ContactRepository contactRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ContactTypeRepository contactTypeRepository;
    @Mock
    private ContactMapper contactMapper;
    @InjectMocks
    private ContactService contactService;

    private User user;
    private User user2;
    private Contact contact;
    private ContactResponseDto contactResponseDto;
    private ContactRequestDto contactRequestDto;
    private ContactResponseDto updateContactResponseDto;


    private UpdateContactRequestDto updateContactRequestDto;
    private ContactType contactType;

    @BeforeEach
    public void init(){

        user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setPassword("password");

        user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@email.com");
        user2.setPassword("password");

        contact = new Contact();
        contact.setFirstName("Test");
        contact.setLastName("Contact");

        contactResponseDto = new ContactResponseDto();
        contactResponseDto.setFirstName("Response");
        contactResponseDto.setLastName("Test");

        contactRequestDto = new ContactRequestDto();
        contactRequestDto.setFirstName("Request");
        contactRequestDto.setLastName("Test");

        updateContactRequestDto = new UpdateContactRequestDto();
        updateContactRequestDto.setEmail("test@gmail.com");
        updateContactRequestDto.setFirstName("Milos");
        updateContactRequestDto.setLastName("Milosevic");

        contactType = new ContactType();
        contactType.setValue("Type");

        updateContactResponseDto = new ContactResponseDto();
        updateContactResponseDto.setEmail("test@gmail.com");
        updateContactResponseDto.setFirstName("Milos");
        updateContactResponseDto.setLastName("Milosevic");
    }

    @Test
    public void CreateContactReturnsContactResponseDto(){

        when(contactMapper.contactRequestDtoToEntity(any(ContactRequestDto.class))).thenReturn(contact);
        when(contactMapper.contactToContactResponseDto(contact)).thenReturn(contactResponseDto);
        when(contactRepository.save(contact)).thenReturn(contact);

        ContactResponseDto expectedDto = contactService.createContact(contactRequestDto, user);

        Assertions.assertThat(expectedDto).isNotNull();
        Assertions.assertThat(contact.getUser()).isEqualTo(user);
        Assertions.assertThat(expectedDto.getFirstName()).isEqualTo(contact.getFirstName());

        verify(contactMapper, times(1)).contactRequestDtoToEntity(any(ContactRequestDto.class));
        verify(contactMapper, times(1)).contactToContactResponseDto(contact);

    }

    @Test
    public void UpdateContactReturnsContactResponseDto() throws ActionNotAllowedException {
        contact.setUser(user);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(contactRepository.findContactByGuid(any(UUID.class))).thenReturn(Optional.of(contact));
        when(contactMapper.updateEntityFromRequest(contact, updateContactRequestDto)).thenReturn(contact);
        when(contactMapper.contactToContactResponseDto(contact)).thenReturn(updateContactResponseDto);

        updateContactResponseDto = contactService.updateContact(UUID.randomUUID(),
                updateContactRequestDto, "email");

        Assertions.assertThat(updateContactResponseDto).isNotNull();
        Assertions.assertThat(updateContactResponseDto.getFirstName()).isEqualTo(updateContactRequestDto.getFirstName());
    }

    @Test
    public void GetContactByGuidReturnsContactResponseDto() {

        when(contactRepository.findContactByGuid(any(UUID.class))).thenReturn(Optional.of(contact));
        when(contactMapper.contactToContactResponseDto(contact)).thenReturn(contactResponseDto);

        ContactResponseDto expectedDto = contactService.findContact(UUID.randomUUID());

        Assertions.assertThat(expectedDto).isNotNull();
        Assertions.assertThat(expectedDto.getFirstName()).isEqualTo(contactResponseDto.getFirstName());
    }

}

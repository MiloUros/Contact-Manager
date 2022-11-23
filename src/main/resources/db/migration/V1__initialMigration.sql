CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE contacts
(
    contact_id   SERIAL NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    first_name   VARCHAR(50),
    last_name    VARCHAR(50),
    email        VARCHAR(50),
    phone_number VARCHAR(20),
    contact_type INTEGER,
    user_id      BIGINT,
    CONSTRAINT pk_contacts PRIMARY KEY (contact_id)
);

CREATE TABLE users
(
    user_id       SERIAL NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    email         VARCHAR(50),
    user_password VARCHAR(20),
    user_role     INTEGER,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE users_users_contacts
(
    user_user_id              BIGINT NOT NULL,
    users_contacts_contact_id BIGINT NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users_users_contacts
    ADD CONSTRAINT uc_users_users_contacts_userscontacts_contact UNIQUE (users_contacts_contact_id);

ALTER TABLE contacts
    ADD CONSTRAINT FK_CONTACTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE users_users_contacts
    ADD CONSTRAINT fk_useusecon_on_contact FOREIGN KEY (users_contacts_contact_id) REFERENCES contacts (contact_id);

ALTER TABLE users_users_contacts
    ADD CONSTRAINT fk_useusecon_on_user FOREIGN KEY (user_user_id) REFERENCES users (user_id);
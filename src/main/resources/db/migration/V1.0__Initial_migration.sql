CREATE TABLE users
(
    PRIMARY KEY (id),
    id            SERIAL,
    first_name    varchar(20),
    last_name     varchar(20),
    email         varchar(40) UNIQUE ,
    password varchar(50),
    role     varchar(50),
    created_at    timestamp,
    updated_at    timestamp
);

CREATE TABLE contacts_types
(
    PRIMARY KEY (id),
    id           SERIAL,
    contact_type_name varchar(20) UNIQUE
);


CREATE TABLE contacts
(
    id              SERIAL,
    first_name      varchar(20),
    last_name       varchar(20),
    email           varchar(40) UNIQUE,
    phone_number    varchar(20),
    contact_type_id int,
    created_at      timestamp,
    updated_at      timestamp,
    user_id         int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (contact_type_id) REFERENCES contacts_types (id)
)


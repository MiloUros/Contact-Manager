CREATE TABLE users
(
    PRIMARY KEY (id),
    id            SERIAL,
    first_name    varchar(20),
    last_name     varchar(20),
    email         varchar(40) UNIQUE,
    user_password varchar(50),
    user_role     varchar(50),
    created_at    timestamp,
    updated_at    timestamp
);

CREATE TABLE contact_type
(
    PRIMARY KEY (id),
    id           SERIAL,
    contact_type varchar(20)
);


CREATE TABLE contacts
(
    id              SERIAL,
    first_name      varchar(20),
    last_name       varchar(20),
    email           varchar(40) UNIQUE,
    phone_number    varchar(20),
    contact_type_id int NOT NULL,
    created_at      timestamp,
    updated_at      timestamp,
    user_id         int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (contact_type_id) REFERENCES contact_type (id)
)


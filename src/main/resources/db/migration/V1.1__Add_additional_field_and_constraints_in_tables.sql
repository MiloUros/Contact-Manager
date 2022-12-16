
ALTER TABLE users ALTER COLUMN email SET NOT NULL;
ALTER TABLE users ALTER COLUMN password SET NOT NULL;
ALTER TABLE users ALTER COLUMN role SET NOT NULL;
ALTER TABLE users ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE users ADD COLUMN guid uuid UNIQUE NOT NULL DEFAULT gen_random_uuid ();

ALTER TABLE contacts_types ALTER COLUMN value SET NOT NULL;
ALTER TABLE contacts_types ADD COLUMN created_at timestamp NOT NULL;
ALTER TABLE contacts_types ADD COLUMN updated_at timestamp;
ALTER TABLE contacts_types ADD COLUMN guid uuid UNIQUE NOT NULL DEFAULT gen_random_uuid ();

ALTER TABLE contacts ADD CONSTRAINT at_least_one_present
    CHECK (COALESCE(first_name, last_name, email, phone_number) IS NOT NULL);

ALTER TABLE contacts ALTER COLUMN  created_at SET NOT NULL;
ALTER TABLE contacts ADD COLUMN address VARCHAR(100);
ALTER TABLE contacts ADD COLUMN info VARCHAR(100);
ALTER TABLE contacts ADD COLUMN guid uuid UNIQUE NOT NULL DEFAULT gen_random_uuid ();


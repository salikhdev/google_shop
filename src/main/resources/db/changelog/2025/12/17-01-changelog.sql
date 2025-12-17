-- liquibase formatted sql

-- changeset salikhdev:1765974367896-4
ALTER TABLE _user
    ADD status SMALLINT;

-- changeset salikhdev:1765974367896-5
ALTER TABLE _user
    ALTER COLUMN status SET NOT NULL;

-- changeset salikhdev:1765974367896-6
DROP SEQUENCE size_seq CASCADE;

-- changeset salikhdev:1765974367896-1
ALTER TABLE _user
    ALTER COLUMN balance TYPE DECIMAL USING (balance::DECIMAL);

-- changeset salikhdev:1765974367896-2
CREATE SEQUENCE IF NOT EXISTS size_id_seq;
ALTER TABLE size
    ALTER COLUMN id SET NOT NULL;
ALTER TABLE size
    ALTER COLUMN id SET DEFAULT nextval('size_id_seq');
ALTER SEQUENCE size_id_seq OWNED BY size.id;

-- changeset salikhdev:1765974367896-3
ALTER TABLE product
    ALTER COLUMN price TYPE DECIMAL USING (price::DECIMAL);


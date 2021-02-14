CREATE TABLE person (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255)
);

CREATE TABLE post (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255),
                        text VARCHAR(255),
                        person_id  BIGSERIAL
                            constraint person_fkey references person
);
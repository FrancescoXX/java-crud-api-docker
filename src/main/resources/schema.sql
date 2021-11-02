CREATE TABLE IF NOT EXISTS EMPLOYEES (
    id serial PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email_address varchar(50) NOT NULL
);
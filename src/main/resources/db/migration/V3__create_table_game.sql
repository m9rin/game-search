CREATE TABLE game (
    id serial PRIMARY KEY,
    title varchar(100) NOT NULL,
    genre varchar(100),
    release_date date,
    rating numeric,
    description text,
    developer varchar,
    publisher varchar,
    created_at timestamp,
    updated_at timestamp
);
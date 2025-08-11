CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(155) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(40) NOT NUll
);

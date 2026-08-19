CREATE TABLE
    medicos (
        id BIGINT NOT NULL,
        genero ENUM ('FEMININO', 'MASCULINO') NOT NULL,
        nome VARCHAR(100) NOT NULL,
        sobrenome VARCHAR(100) NOT NULL,
        crm VARCHAR(13) NOT NULL UNIQUE,
        especialidade ENUM (
            'ORTOPEDIA',
            'CARDIOLOGIA',
            'GINECOLOGIA',
            'DERMATOLOGIA'
        ) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_medicos_usuarios FOREIGN KEY (id) REFERENCES usuarios (id)
    );
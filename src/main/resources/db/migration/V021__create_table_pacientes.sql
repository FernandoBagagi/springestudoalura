CREATE TABLE
    pacientes (
        id BIGINT NOT NULL,
        nome VARCHAR(100) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE,
        cpf VARCHAR(14) NOT NULL UNIQUE,
        nascimento DATE NOT NULL,
        telefone VARCHAR(13) NOT NULL,
        -- Endereço
        logradouro VARCHAR(100) NOT NULL,
        numero VARCHAR(20),
        complemento VARCHAR(100),
        bairro VARCHAR(100) NOT NULL,
        cidade VARCHAR(100) NOT NULL,
        uf VARCHAR(2) NOT NULL,
        cep VARCHAR(9) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_pacientes_usuarios FOREIGN KEY (id) REFERENCES usuarios (id)
    );
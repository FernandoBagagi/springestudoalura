CREATE TABLE
    usuarios (
        id BIGINT NOT NULL AUTO_INCREMENT,
        login VARCHAR(100) NOT NULL UNIQUE,
        email VARCHAR(100) NOT NULL UNIQUE,
        senha VARCHAR(255) NOT NULL,
        perfil ENUM ('ATENDENTE', 'MEDICO', 'PACIENTE') NOT NULL,
        ativo TINYINT (1) NOT NULL DEFAULT 1,
        PRIMARY KEY (id)
    );
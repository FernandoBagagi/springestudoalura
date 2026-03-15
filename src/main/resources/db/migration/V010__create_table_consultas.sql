CREATE TABLE
    consultas (
        id BIGINT NOT NULL AUTO_INCREMENT,
        id_medico BIGINT NOT NULL,
        id_paciente BIGINT NOT NULL,
        dia DATE NOT NULL,
        hora TIME(0) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_consultas_id_medico FOREIGN KEY (id_medico) REFERENCES medicos (id),
        CONSTRAINT fk_consultas_id_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes (id),
        CONSTRAINT chk_consultas_segundos_zerados CHECK (
            EXTRACT(
                SECOND
                FROM
                    hora
            ) = 0
        ),
        CONSTRAINT chk_consultas_minutos_multiplos_quinze CHECK (
            MOD(
                EXTRACT(
                    MINUTE
                    FROM
                        hora
                ),
                15
            ) = 0
        ),
        CONSTRAINT uq_consultas_id_medico_dia_hora UNIQUE (id_medico, dia, hora)
    );
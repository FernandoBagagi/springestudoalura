CREATE TABLE
	consultas (
        id BIGINT NOT NULL AUTO_INCREMENT,
        medico_id BIGINT NOT NULL,
        paciente_id BIGINT NOT NULL,
        dia DATE NOT NULL,
        hora TIME(0) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_consultas_medicos FOREIGN KEY (medico_id) REFERENCES medicos (id),
        CONSTRAINT fk_consultas_paciented FOREIGN KEY (paciente_id) REFERENCES pacientes (id),
        CONSTRAINT chk_consultas_segundos_zerados CHECK (
            EXTRACT(SECOND FROM hora) = 0
        ),
        CONSTRAINT chk_consultas_minutos_multiplos_quinze CHECK (
            MOD(
                EXTRACT(MINUTE FROM hora),
                15
            ) = 0
        ),
        CONSTRAINT uq_consultas_medico_id_dia_hora UNIQUE (medico_id, dia, hora),
        CONSTRAINT uq_consultas_paciente_id_dia_hora UNIQUE (paciente_id, dia, hora)
    );
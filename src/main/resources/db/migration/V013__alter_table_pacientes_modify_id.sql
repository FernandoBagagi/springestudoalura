ALTER TABLE consultas
DROP FOREIGN KEY fk_consultas_id_paciente;

ALTER TABLE pacientes MODIFY id BIGINT NOT NULL;

ALTER TABLE consultas ADD CONSTRAINT fk_consultas_id_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes (id);
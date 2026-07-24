ALTER TABLE consultas
DROP FOREIGN KEY fk_consultas_id_medico;

ALTER TABLE medicos MODIFY id BIGINT NOT NULL;

ALTER TABLE consultas ADD CONSTRAINT fk_consultas_id_medico FOREIGN KEY (id_medico) REFERENCES medicos (id);
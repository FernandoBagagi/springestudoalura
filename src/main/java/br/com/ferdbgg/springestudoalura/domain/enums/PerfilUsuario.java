package br.com.ferdbgg.springestudoalura.domain.enums;

import java.util.stream.Stream;

public enum PerfilUsuario {
    ATENDENTE, MEDICO, PACIENTE;

    public static PerfilUsuario parse(String perfil) {

        return Stream.of(values())
                .filter(p -> String.valueOf(p).equalsIgnoreCase(perfil))
                .findFirst()
                .orElse(null);

    }
}

package br.com.ferdbgg.springestudoalura.domain.enums;

public enum EspecialidadeMedico {

    ORTOPEDIA,
    CARDIOLOGIA,
    GINECOLOGIA,
    DERMATOLOGIA;

    public static EspecialidadeMedico parse(String especialidade) {
        
        for (EspecialidadeMedico especialidadeMedico : values()) {

            if (String.valueOf(especialidadeMedico).equalsIgnoreCase(especialidade)) {
                return especialidadeMedico;
            }
            
        }

        return null;

    }

}

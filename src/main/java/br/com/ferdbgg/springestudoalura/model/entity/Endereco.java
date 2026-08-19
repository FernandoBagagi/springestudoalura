package br.com.ferdbgg.springestudoalura.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private static final String VIRGULA = ",";
    private static final String ESPACO = " ";

    @Column(nullable = false)
    private String logradouro;
    
    private String numero;

    private String complemento;
    
    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 9)
    private String cep;

    @Override
    public String toString() {

        final var buffer = new StringBuilder();
        
        buffer.append(logradouro);
        buffer.append(VIRGULA);
        buffer.append(ESPACO);

        buffer.append(numero == null || numero.isBlank() ? "S/N" : numero);
        buffer.append(VIRGULA);
        buffer.append(ESPACO);

        if (complemento != null && !complemento.isBlank()) {
            buffer.append(complemento);
            buffer.append(VIRGULA);
            buffer.append(ESPACO);
        }

        buffer.append(bairro);
        buffer.append(VIRGULA);
        buffer.append(ESPACO);

        buffer.append(cidade);
        buffer.append("-");
        buffer.append(uf);
        buffer.append(VIRGULA);
        buffer.append(ESPACO);

        buffer.append(cep);

        return buffer.toString();

    }

}

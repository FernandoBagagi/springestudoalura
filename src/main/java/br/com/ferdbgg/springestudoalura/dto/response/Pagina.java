package br.com.ferdbgg.springestudoalura.dto.response;

import java.util.List;

public record Pagina<T>(
    List<T> conteudo,
    MetadadosPagina metadados
) {
    
}

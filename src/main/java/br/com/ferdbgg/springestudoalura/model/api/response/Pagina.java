package br.com.ferdbgg.springestudoalura.model.api.response;

import java.util.List;

public record Pagina<T>(

        List<T> conteudo,

        MetadadosPagina metadados

) {

}

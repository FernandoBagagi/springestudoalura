package br.com.ferdbgg.springestudoalura.domain.dto.response;

public record MetadadosPagina(
        int numeroPagina,
        int numeroElementosPorPagina,
        long totalElementos,
        int totalPaginas) {

}

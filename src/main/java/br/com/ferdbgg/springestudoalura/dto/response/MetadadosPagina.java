package br.com.ferdbgg.springestudoalura.dto.response;

public record MetadadosPagina(
        int numeroPagina,
        int numeroElementosPorPagina,
        long totalElementos,
        int totalPaginas) {

}

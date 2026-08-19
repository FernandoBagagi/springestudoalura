package br.com.ferdbgg.springestudoalura.model.api.response;

public record MetadadosPagina(

                int numeroPagina,

                int numeroElementosPorPagina,

                long totalElementos,

                int totalPaginas

) {

}

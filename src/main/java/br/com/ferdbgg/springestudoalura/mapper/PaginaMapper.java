package br.com.ferdbgg.springestudoalura.mapper;

import org.springframework.data.domain.Page;

import br.com.ferdbgg.springestudoalura.dto.response.MetadadosPagina;
import br.com.ferdbgg.springestudoalura.dto.response.Pagina;

public class PaginaMapper {
    
    private PaginaMapper() {

    }

    public static <T> Pagina<T> map(Page<T> page) {

        final MetadadosPagina metadados = new MetadadosPagina(
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages());

        return new Pagina<>(page.getContent(), metadados);
        
    }
}

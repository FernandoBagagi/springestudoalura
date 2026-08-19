package br.com.ferdbgg.springestudoalura.model.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.response.MetadadosPagina;
import br.com.ferdbgg.springestudoalura.model.api.response.Pagina;

@Component
public class PaginaMapper {
    
    public <T> Pagina<T> parsePagina(Page<T> page) {

        final MetadadosPagina metadados = new MetadadosPagina(
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages());

        return new Pagina<>(page.getContent(), metadados);
        
    }
}

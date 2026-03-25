package br.com.ferdbgg.springestudoalura.controller.web;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.response.Pagina;
import br.com.ferdbgg.springestudoalura.service.MedicoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/web/medicos")
@RequiredArgsConstructor
public class MedicoAction {

    private final MedicoService medicoService;

    @GetMapping
    public String listar(Pageable pageable, Model model) {

        final Pagina<DadosBasicosMedico> pagina = medicoService.listar(pageable);

        model.addAttribute("medicos", pagina.conteudo());
        model.addAttribute("pagina", pagina);

        return "medicos";

    }

}

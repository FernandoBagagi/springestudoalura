package br.com.ferdbgg.springestudoalura.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/web/", "/web/home", "/web/index"})
public class IndexController {

    @GetMapping
    public String carregarPaginaIndex() {
        return "index";
    }

}

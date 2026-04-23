package br.com.ferdbgg.springestudoalura.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/login")
public class LoginAction {

    @GetMapping
    public String telaLogin() {
        return "autenticacao/login";
    }

    @GetMapping("/logout")
    public String carregaPaginaLogout() {
        return "autenticacao/logout";
    }

}

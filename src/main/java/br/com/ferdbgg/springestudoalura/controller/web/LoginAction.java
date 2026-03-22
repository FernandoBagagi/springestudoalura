package br.com.ferdbgg.springestudoalura.controller.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ferdbgg.springestudoalura.form.LoginForm;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/web/login")
@RequiredArgsConstructor
public class LoginAction {

    private final AuthenticationManager manager;

    @GetMapping
    public String telaLogin() {
        return "login";
    }

    @PostMapping
    public String fazerLogin(
            @ModelAttribute LoginForm form,
            RedirectAttributes redirectAttributes) {

        try {
            
            final UsernamePasswordAuthenticationToken authentication //
                = new UsernamePasswordAuthenticationToken(form.login(),form.senha());

            manager.authenticate(authentication);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/web/login";
        }

        return "redirect:/web/medicos";

    }

}

package br.com.ferdbgg.springestudoalura.model.entity;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.ferdbgg.springestudoalura.model.enums.PerfilUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    @Column(nullable = false)
    private Boolean ativo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        final var authority = new SimpleGrantedAuthority(perfil.name());
        return Collections.singletonList(authority);

    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    public boolean isMedico() {
        return PerfilUsuario.MEDICO.equals(perfil);
    }

    public boolean isPaciente() {
        return PerfilUsuario.PACIENTE.equals(perfil);
    }

    public boolean isAtendente() {
        return PerfilUsuario.ATENDENTE.equals(perfil);
    }

}

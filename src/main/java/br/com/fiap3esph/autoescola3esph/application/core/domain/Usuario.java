package br.com.fiap3esph.autoescola3esph.application.core.domain;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Usuario implements UserDetails {
    private Long id;
    private String login;
    private String senha;
    private Perfil perfil;

    public Usuario() {
    }

    public Usuario(Long id, String login, String senha, Perfil perfil) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void atualizarPerfil(Perfil perfil) {
        if (perfil != null) {
            this.perfil = perfil;
        }
    }

    public void alterarSenha(String novaSenhaCriptografada) {
        this.senha = novaSenhaCriptografada;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank
        @Size(min = 3, max = 100)
        String login,

        @NotBlank
        @Size(min = 6, max = 100, message = "deve ter no mínimo 6 caracteres")
        String senha,

        @NotNull
        Perfil perfil) {
}

package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAlteracaoSenha(
        @NotBlank
        @JsonProperty("senha_atual")
        String senhaAtual,

        @NotBlank
        @Size(min = 6, max = 100, message = "deve ter no mínimo 6 caracteres")
        @JsonProperty("nova_senha")
        String novaSenha) {
}

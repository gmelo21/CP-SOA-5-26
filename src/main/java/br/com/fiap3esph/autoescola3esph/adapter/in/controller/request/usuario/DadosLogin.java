package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(
        @NotBlank
        String login,

        @NotBlank
        String senha) {
}

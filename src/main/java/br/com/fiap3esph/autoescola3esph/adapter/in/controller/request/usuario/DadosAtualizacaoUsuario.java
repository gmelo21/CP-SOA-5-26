package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Perfil;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,

        @NotNull
        Perfil perfil) {
}

package br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.usuario;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Perfil;

public record DadosDetalhamentoUsuario(
        Long id,
        String login,
        Perfil perfil) {
}

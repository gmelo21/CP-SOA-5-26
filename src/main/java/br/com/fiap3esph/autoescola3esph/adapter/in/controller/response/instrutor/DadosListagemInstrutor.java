package br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;

public record DadosListagemInstrutor(
        Long id,
        String nome,
        String email,
        String cnh,
        Especialidade especialidade) {
}

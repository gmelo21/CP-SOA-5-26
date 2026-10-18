package br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.dto.DadosEndereco;

public record DadosDetalhamentoInstrutor(
        Long id,
        String nome,
        String email,
        String telefone,
        String cnh,
        Especialidade especialidade,
        DadosEndereco endereco,
        boolean ativo) {
}

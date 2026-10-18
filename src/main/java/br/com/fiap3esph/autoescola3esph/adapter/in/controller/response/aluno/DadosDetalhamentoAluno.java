package br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.aluno;

import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.dto.DadosEndereco;

public record DadosDetalhamentoAluno(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco,
        boolean ativo) {
}

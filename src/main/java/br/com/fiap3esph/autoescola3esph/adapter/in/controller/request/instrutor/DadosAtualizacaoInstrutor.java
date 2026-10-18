package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrutor;

import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.dto.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoInstrutor(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}

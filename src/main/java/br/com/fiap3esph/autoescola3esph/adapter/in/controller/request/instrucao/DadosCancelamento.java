package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.MotivoCancelamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamento(
        @NotNull
        @JsonProperty("id_instrucao")
        Long idInstrucao,

        @NotNull(message = "é obrigatório informar o motivo do cancelamento")
        MotivoCancelamento motivo) {
}

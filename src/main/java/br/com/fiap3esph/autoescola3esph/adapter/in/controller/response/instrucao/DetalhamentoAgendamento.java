package br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrucao;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.MotivoCancelamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DetalhamentoAgendamento(
        Long id,

        @JsonProperty("id_aluno")
        Long idAluno,

        @JsonProperty("nome_aluno")
        String nomeAluno,

        @JsonProperty("id_instrutor")
        Long idInstrutor,

        @JsonProperty("nome_instrutor")
        String nomeInstrutor,
        Especialidade especialidade,

        @JsonProperty("data_hora")
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime dataHora,
        boolean cancelada,

        @JsonProperty("motivo_cancelamento")
        MotivoCancelamento motivoCancelamento,

        @JsonProperty("data_cancelamento")
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime dataCancelamento) {

    public DetalhamentoAgendamento(Instrucao instrucao) {
        this(
                instrucao.getId(),
                instrucao.getAluno().getId(),
                instrucao.getAluno().getNome(),
                instrucao.getInstrutor().getId(),
                instrucao.getInstrutor().getNome(),
                instrucao.getInstrutor().getEspecialidade(),
                instrucao.getDataHora(),
                instrucao.isCancelada(),
                instrucao.getMotivoCancelamento(),
                instrucao.getDataCancelamento()
        );
    }
}

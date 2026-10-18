package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamento(
        @NotNull
        @JsonProperty("id_aluno")
        Long idAluno,

        @JsonProperty("id_instrutor")
        Long idInstrutor,
        Especialidade especialidade,

        @NotNull
        @Future
        @JsonProperty("data_hora")
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime dataHora) {
}

package br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrucao;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DadosListagemInstrucao(
        Long id,

        @JsonProperty("nome_aluno")
        String nomeAluno,

        @JsonProperty("nome_instrutor")
        String nomeInstrutor,

        @JsonProperty("data_hora")
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        LocalDateTime dataHora,
        boolean cancelada) {

    public DadosListagemInstrucao(Instrucao instrucao) {
        this(
                instrucao.getId(),
                instrucao.getAluno().getNome(),
                instrucao.getInstrutor().getNome(),
                instrucao.getDataHora(),
                instrucao.isCancelada()
        );
    }
}

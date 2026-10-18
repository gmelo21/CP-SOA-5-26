package br.com.fiap3esph.autoescola3esph.application.core.domain;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.MotivoCancelamento;

import java.time.LocalDateTime;

public class Instrucao {
    private Long id;
    private Aluno aluno;
    private Instrutor instrutor;
    private LocalDateTime dataHora;
    private MotivoCancelamento motivoCancelamento;
    private LocalDateTime dataCancelamento;

    public Instrucao() {
    }

    public Instrucao(
            Long id,
            Aluno aluno,
            Instrutor instrutor,
            LocalDateTime dataHora) {
        this(id, aluno, instrutor, dataHora, null, null);
    }

    public Instrucao(
            Long id,
            Aluno aluno,
            Instrutor instrutor,
            LocalDateTime dataHora,
            MotivoCancelamento motivoCancelamento,
            LocalDateTime dataCancelamento) {
        this.id = id;
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.dataHora = dataHora;
        this.motivoCancelamento = motivoCancelamento;
        this.dataCancelamento = dataCancelamento;
    }

    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public MotivoCancelamento getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public boolean isCancelada() {
        return motivoCancelamento != null;
    }

    public void cancelar(MotivoCancelamento motivo) {
        this.motivoCancelamento = motivo;
        this.dataCancelamento = LocalDateTime.now();
    }
}

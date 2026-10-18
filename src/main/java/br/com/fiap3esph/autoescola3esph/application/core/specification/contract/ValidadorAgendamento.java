package br.com.fiap3esph.autoescola3esph.application.core.specification.contract;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;

public interface ValidadorAgendamento {
    void validar(DadosAgendamento dados);
}

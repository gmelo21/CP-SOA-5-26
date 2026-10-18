package br.com.fiap3esph.autoescola3esph.application.core.specification.contract;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;

public interface ValidadorCancelamento {
    void validar(Instrucao instrucao);
}

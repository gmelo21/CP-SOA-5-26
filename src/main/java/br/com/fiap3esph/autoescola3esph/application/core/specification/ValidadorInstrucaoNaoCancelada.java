package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorCancelamento;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorInstrucaoNaoCancelada implements ValidadorCancelamento {
    @Override
    public void validar(Instrucao instrucao) {
        if (instrucao.isCancelada()) {
            throw new ValidacaoException("Esta instrução já foi cancelada!");
        }
    }
}

package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorCancelamento;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorAntecedenciaCancelamento implements ValidadorCancelamento {
    private static final long ANTECEDENCIA_MINIMA_EM_HORAS = 24;

    @Override
    public void validar(Instrucao instrucao) {
        long antecedencia = Duration
                .between(LocalDateTime.now(), instrucao.getDataHora())
                .toHours();

        if (antecedencia < ANTECEDENCIA_MINIMA_EM_HORAS) {
            throw new ValidacaoException(
                    "A instrução só pode ser cancelada com antecedência mínima de 24 horas!");
        }
    }
}

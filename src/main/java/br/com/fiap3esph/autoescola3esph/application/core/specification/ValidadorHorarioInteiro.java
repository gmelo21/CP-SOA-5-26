package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorHorarioInteiro implements ValidadorAgendamento {
    @Override
    public void validar(DadosAgendamento dados) {
        LocalDateTime dataEscolhida = dados.dataHora();

        if (dataEscolhida.getMinute() != 0
) {
            throw new ValidacaoException("Este campo deve ser preenchido apenas com horas inteiras (ex: 09:00, 13:00,...");
        }
    }
}

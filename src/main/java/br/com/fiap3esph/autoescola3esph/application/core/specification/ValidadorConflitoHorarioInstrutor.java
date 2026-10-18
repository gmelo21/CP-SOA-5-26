package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorConflitoHorarioInstrutor implements ValidadorAgendamento {
    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamento dados) {
        if (dados.idInstrutor() == null) {
            return;
        }

        boolean instrutorOcupado = repository.existeInstrutorOcupadoEm(
                dados.idInstrutor(),
                dados.dataHora()
        );

        if (instrutorOcupado) {
            throw new ValidacaoException("Instrutor indisponível na data/hora escolhida!");
        }
    }
}

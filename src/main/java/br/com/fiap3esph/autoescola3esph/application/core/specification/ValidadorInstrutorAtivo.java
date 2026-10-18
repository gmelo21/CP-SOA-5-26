package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrutorRepository;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorInstrutorAtivo implements ValidadorAgendamento {
    private final InstrutorRepository instrutorRepository;

    @Override
    public void validar(DadosAgendamento dados) {
        if (dados.idInstrutor() == null) {
            return;
        }
        if (instrutorRepository.existeInativoComId(dados.idInstrutor())) {
            throw new ValidacaoException("Não pode agendar instrução com instrutor inativo!");
        }
    }
}

package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.application.port.out.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorAlunoAtivo implements ValidadorAgendamento {
    private final AlunoRepository alunoRepository;

    @Override
    public void validar(DadosAgendamento dados) {
        if (alunoRepository.existeInativoComId(dados.idAluno())) {
            throw new ValidacaoException("Não pode agendar instrução para aluno inativo!");
        }
    }
}

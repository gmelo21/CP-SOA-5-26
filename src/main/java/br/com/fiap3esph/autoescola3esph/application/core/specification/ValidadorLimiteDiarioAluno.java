package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamento {
    private static final long LIMITE_DIARIO = 2;

    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamento dados) {
        LocalDateTime inicioDoDia = dados.dataHora().toLocalDate().atStartOfDay();
        LocalDateTime fimDoDia = inicioDoDia.plusDays(1).minusNanos(1);

        long agendamentosNoDia = repository.contarAgendamentosDoAlunoNoDia(
                dados.idAluno(),
                inicioDoDia,
                fimDoDia
        );

        if (agendamentosNoDia >= LIMITE_DIARIO) {
            throw new ValidacaoException(
                    "O aluno já possui duas instruções agendadas para este dia!");
        }
    }
}

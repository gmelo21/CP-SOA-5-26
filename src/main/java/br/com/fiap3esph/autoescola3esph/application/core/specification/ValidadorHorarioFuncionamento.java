package br.com.fiap3esph.autoescola3esph.application.core.specification;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento {
    private static final int ABERTURA = 6;
    private static final int FECHAMENTO = 21;
    private static final int DURACAO_DA_INSTRUCAO_EM_HORAS = 1;

    @Override
    public void validar(DadosAgendamento dados) {
        LocalDateTime dataEscolhida = dados.dataHora();

        boolean domingo = dataEscolhida.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean preAbertura = dataEscolhida.getHour() < ABERTURA;
        boolean posFechamento =
                dataEscolhida.getHour() > (FECHAMENTO - DURACAO_DA_INSTRUCAO_EM_HORAS);

        if (domingo || preAbertura || posFechamento) {
            throw new ValidacaoException(
                    "Tentativa de agendamento fora do horário de funcionamento! "
                            + "A auto-escola atende de segunda a sábado, das 06:00 às 21:00.");
        }
    }
}

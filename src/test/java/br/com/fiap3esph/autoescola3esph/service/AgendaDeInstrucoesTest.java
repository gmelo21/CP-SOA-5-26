package br.com.fiap3esph.autoescola3esph.service;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosCancelamento;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Aluno;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrutor;
import br.com.fiap3esph.autoescola3esph.application.core.specification.ValidadorAntecedenciaCancelamento;
import br.com.fiap3esph.autoescola3esph.application.core.specification.ValidadorInstrucaoNaoCancelada;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorCancelamento;
import br.com.fiap3esph.autoescola3esph.application.port.out.AlunoRepository;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrutorRepository;
import br.com.fiap3esph.autoescola3esph.application.service.AgendaDeInstrucoes;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.MotivoCancelamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Serviço de agenda de instruções")
class AgendaDeInstrucoesTest {
    private InstrucaoRepository repository;
    private AgendaDeInstrucoes agenda;

    @BeforeEach
    void setUp() {
        repository = mock(InstrucaoRepository.class);

        List<ValidadorAgendamento> validadoresAgendamento = List.of();
        List<ValidadorCancelamento> validadoresCancelamento = List.of(
                new ValidadorInstrucaoNaoCancelada(),
                new ValidadorAntecedenciaCancelamento()
        );

        agenda = new AgendaDeInstrucoes(
                repository,
                mock(AlunoRepository.class),
                mock(InstrutorRepository.class),
                validadoresAgendamento,
                validadoresCancelamento
        );
    }

    @Test
    @DisplayName("Recusa o cancelamento com menos de 24 horas de antecedência.")
    void cancelarCenario1() {
        Instrucao daqui5Horas = new Instrucao(
                10L,
                aluno(),
                instrutor(),
                LocalDateTime.now().plusHours(5)
        );
        when(repository.buscarPorId(10L)).thenReturn(Optional.of(daqui5Horas));

        assertThatThrownBy(() -> agenda.cancelar(
                new DadosCancelamento(10L, MotivoCancelamento.OUTROS)))
                .isInstanceOf(ValidacaoException.class)
                .hasMessageContaining("24 horas");

        verify(repository, never()).salvar(any());
    }

    private Endereco endereco() {
        return new Endereco("Avenida Paulista", "1106", null,
                "Bela Vista", "São Paulo", "SP", "01311-000");
    }

    private Aluno aluno() {
        return new Aluno(1L, "Aluno Teste", "aluno.teste@email.com.br",
                "(11) 98765-4321", "12345678901", endereco(), true);
    }

    private Instrutor instrutor() {
        return new Instrutor(2L, "Instrutor Teste", "instrutor.teste@email.com.br",
                "(11) 91234-5678", "01234567890", Especialidade.CARROS, endereco(), true);
    }
}

package br.com.fiap3esph.autoescola3esph.application.service;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosCancelamento;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrucao.DadosListagemInstrucao;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrucao.DetalhamentoAgendamento;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Aluno;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrutor;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3esph.autoescola3esph.application.core.specification.contract.ValidadorCancelamento;
import br.com.fiap3esph.autoescola3esph.application.port.out.AlunoRepository;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrutorRepository;
import br.com.fiap3esph.autoescola3esph.exception.type.AlunoNaoEncontradoException;
import br.com.fiap3esph.autoescola3esph.exception.type.InstrucaoNaoEncontradaException;
import br.com.fiap3esph.autoescola3esph.exception.type.InstrutorNaoEncontradoException;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaDeInstrucoes {
    private final InstrucaoRepository repository;
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final List<ValidadorAgendamento> validadoresAgendamento;
    private final List<ValidadorCancelamento> validadoresCancelamento;

    @Transactional
    public DetalhamentoAgendamento agendar(DadosAgendamento dados) {
        Aluno aluno = alunoRepository
                .buscarPorId(dados.idAluno())
                .orElseThrow(() -> new AlunoNaoEncontradoException("Id do aluno informado não existe!"));

        if (dados.idInstrutor() != null && !instrutorRepository.existePorId(dados.idInstrutor())) {
            throw new InstrutorNaoEncontradoException("Id do instrutor informado não existe!");
        }

        validadoresAgendamento.forEach(validador -> validador.validar(dados));

        Instrutor instrutor = escolherInstrutor(dados);
        if (instrutor == null) {
            throw new ValidacaoException("Não há instrutor disponível para a data/hora escolhida!");
        }

        Instrucao instrucao = new Instrucao(null, aluno, instrutor, dados.dataHora());
        return new DetalhamentoAgendamento(repository.salvar(instrucao));
    }

    @Transactional
    public DetalhamentoAgendamento cancelar(DadosCancelamento dados) {
        Instrucao instrucao = repository
                .buscarPorId(dados.idInstrucao())
                .orElseThrow(() ->
                        new InstrucaoNaoEncontradaException("Id da instrução informada não existe!"));

        validadoresCancelamento.forEach(validador -> validador.validar(instrucao));

        instrucao.cancelar(dados.motivo());
        return new DetalhamentoAgendamento(repository.salvar(instrucao));
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemInstrucao> listar(Pageable paginacao) {
        return repository.listar(paginacao).map(DadosListagemInstrucao::new);
    }

    @Transactional(readOnly = true)
    public DetalhamentoAgendamento detalhar(Long id) {
        return repository
                .buscarPorId(id)
                .map(DetalhamentoAgendamento::new)
                .orElseThrow(() ->
                        new InstrucaoNaoEncontradaException("Id da instrução informada não existe!"));
    }

    private Instrutor escolherInstrutor(DadosAgendamento dados) {
        if (dados.idInstrutor() != null) {
            return instrutorRepository
                    .buscarPorId(dados.idInstrutor())
                    .orElseThrow(() ->
                            new InstrutorNaoEncontradoException("Id do instrutor informado não existe!"));
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException(
                    "Especialidade é obrigatória, caso o instrutor não seja informado!");
        }
        return instrutorRepository.escolherInstrutorAleatorioDisponivel(
                dados.especialidade(),
                dados.dataHora()
        );
    }
}

package br.com.fiap3esph.autoescola3esph.application.service;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.mapper.AlunoMapper;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.aluno.DadosAtualizacaoAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.aluno.DadosCadastroAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.aluno.DadosDetalhamentoAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.aluno.DadosListagemAluno;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Aluno;
import br.com.fiap3esph.autoescola3esph.application.port.out.AlunoRepository;
import br.com.fiap3esph.autoescola3esph.exception.type.AlunoNaoEncontradoException;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.mapper.EnderecoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository repository;
    private final AlunoMapper mapper;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoService enderecoService;

    @Transactional
    public DadosDetalhamentoAluno cadastrarAluno(DadosCadastroAluno dados) {
        if (repository.existePorEmail(dados.email())) {
            throw new ValidacaoException("Já existe um aluno cadastrado com este e-mail!");
        }
        if (repository.existePorCpf(dados.cpf())) {
            throw new ValidacaoException("Já existe um aluno cadastrado com este CPF!");
        }

        Endereco endereco = enderecoService.completar(enderecoMapper.toVo(dados.endereco()));
        Aluno aluno = mapper.toDomain(dados, endereco);
        Aluno saved = repository.salvar(aluno);
        return mapper.toDetails(saved);
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemAluno> listarAlunos(Pageable paginacao) {
        return repository
                .listarAtivos(paginacao)
                .map(mapper::toList);
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoAluno detalharAluno(Long id) {
        return mapper.toDetails(buscar(id));
    }

    @Transactional
    public DadosDetalhamentoAluno atualizarAluno(DadosAtualizacaoAluno dados) {
        Aluno aluno = buscar(dados.id());
        aluno.atualizarInformacoes(
                dados.nome(),
                dados.telefone(),
                enderecoMapper.toVo(dados.endereco())
        );
        Aluno saved = repository.salvar(aluno);
        return mapper.toDetails(saved);
    }

    @Transactional
    public void excluirAluno(Long id) {
        Aluno aluno = buscar(id);
        aluno.excluir();
        repository.salvar(aluno);
    }

    private Aluno buscar(Long id) {
        return repository
                .buscarPorId(id)
                .orElseThrow(() ->
                        new AlunoNaoEncontradoException("ID do aluno informado não existe!"));
    }
}

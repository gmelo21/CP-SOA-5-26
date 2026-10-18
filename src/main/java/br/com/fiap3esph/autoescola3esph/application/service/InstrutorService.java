package br.com.fiap3esph.autoescola3esph.application.service;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.mapper.InstrutorMapper;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrutor.DadosAtualizacaoInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrutor.DadosCadastroInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor.DadosListagemInstrutor;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrutor;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrutorRepository;
import br.com.fiap3esph.autoescola3esph.exception.type.InstrutorNaoEncontradoException;
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
public class InstrutorService {
    private final InstrutorRepository repository;
    private final InstrutorMapper mapper;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoService enderecoService;

    @Transactional
    public DadosDetalhamentoInstrutor cadastrarInstrutor(DadosCadastroInstrutor dados) {
        if (repository.existePorEmail(dados.email())) {
            throw new ValidacaoException("Já existe um instrutor cadastrado com este e-mail!");
        }
        if (repository.existePorCnh(dados.cnh())) {
            throw new ValidacaoException("Já existe um instrutor cadastrado com esta CNH!");
        }

        Endereco endereco = enderecoService.completar(enderecoMapper.toVo(dados.endereco()));
        Instrutor instrutor = mapper.toDomain(dados, endereco);
        Instrutor saved = repository.salvar(instrutor);
        return mapper.toDetails(saved);
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemInstrutor> listarInstrutores(Pageable paginacao) {
        return repository
                .listarAtivos(paginacao)
                .map(mapper::toList);
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoInstrutor detalharInstrutor(Long id) {
        return mapper.toDetails(buscar(id));
    }

    @Transactional
    public DadosDetalhamentoInstrutor atualizarInstrutor(DadosAtualizacaoInstrutor dados) {
        Instrutor instrutor = buscar(dados.id());
        instrutor.atualizarInformacoes(
                dados.nome(),
                dados.telefone(),
                enderecoMapper.toVo(dados.endereco())
        );
        Instrutor saved = repository.salvar(instrutor);
        return mapper.toDetails(saved);
    }

    @Transactional
    public void excluirInstrutor(Long id) {
        Instrutor instrutor = buscar(id);
        instrutor.excluir();
        repository.salvar(instrutor);
    }

    private Instrutor buscar(Long id) {
        return repository
                .buscarPorId(id)
                .orElseThrow(() ->
                        new InstrutorNaoEncontradoException("ID do instrutor informado não existe!"));
    }
}

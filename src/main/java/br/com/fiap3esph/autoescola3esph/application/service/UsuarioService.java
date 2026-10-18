package br.com.fiap3esph.autoescola3esph.application.service;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.mapper.UsuarioMapper;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosAlteracaoSenha;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosAtualizacaoUsuario;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.usuario.DadosListagemUsuario;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import br.com.fiap3esph.autoescola3esph.application.port.out.UsuarioRepository;
import br.com.fiap3esph.autoescola3esph.exception.type.UsuarioNaoEncontradoException;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DadosDetalhamentoUsuario cadastrarUsuario(DadosCadastroUsuario dados) {
        if (repository.existePorLogin(dados.login())) {
            throw new ValidacaoException("Já existe um usuário cadastrado com este login!");
        }

        Usuario usuario = new Usuario(
                null,
                dados.login(),
                passwordEncoder.encode(dados.senha()),
                dados.perfil()
        );
        return mapper.toDetails(repository.salvar(usuario));
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemUsuario> listarUsuarios(Pageable paginacao) {
        return repository.listar(paginacao).map(mapper::toList);
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoUsuario detalharUsuario(Long id) {
        return mapper.toDetails(buscar(id));
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizarPerfil(DadosAtualizacaoUsuario dados) {
        Usuario usuario = buscar(dados.id());
        usuario.atualizarPerfil(dados.perfil());
        return mapper.toDetails(repository.salvar(usuario));
    }

    @Transactional
    public void excluirUsuario(Long id) {
        if (!repository.existePorId(id)) {
            throw new UsuarioNaoEncontradoException("ID do usuário informado não existe!");
        }
        repository.excluirPorId(id);
    }

    @Transactional
    public void alterarPropriaSenha(String login, DadosAlteracaoSenha dados) {
        Usuario usuario = repository
                .buscarPorLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário autenticado não encontrado!"));

        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            throw new ValidacaoException("A senha atual informada está incorreta!");
        }
        if (passwordEncoder.matches(dados.novaSenha(), usuario.getSenha())) {
            throw new ValidacaoException("A nova senha deve ser diferente da senha atual!");
        }

        usuario.alterarSenha(passwordEncoder.encode(dados.novaSenha()));
        repository.salvar(usuario);
    }

    private Usuario buscar(Long id) {
        return repository
                .buscarPorId(id)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("ID do usuário informado não existe!"));
    }
}

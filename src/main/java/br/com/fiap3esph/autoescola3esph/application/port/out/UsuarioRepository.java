package br.com.fiap3esph.autoescola3esph.application.port.out;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> buscarPorLogin(String login);

    boolean existePorLogin(String login);

    Page<Usuario> listar(Pageable paginacao);

    Optional<Usuario> buscarPorId(Long id);

    Usuario salvar(Usuario usuario);

    void excluirPorId(Long id);

    boolean existePorId(Long id);
}

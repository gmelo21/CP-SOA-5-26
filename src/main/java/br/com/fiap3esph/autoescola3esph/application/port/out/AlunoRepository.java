package br.com.fiap3esph.autoescola3esph.application.port.out;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AlunoRepository {
    Page<Aluno> listarAtivos(Pageable paginacao);

    boolean existeInativoComId(Long id);

    boolean existePorEmail(String email);

    boolean existePorCpf(String cpf);

    boolean existePorId(Long id);

    Aluno salvar(Aluno aluno);

    Optional<Aluno> buscarPorId(Long id);
}

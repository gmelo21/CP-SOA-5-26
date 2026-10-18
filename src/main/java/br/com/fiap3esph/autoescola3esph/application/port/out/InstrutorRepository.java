package br.com.fiap3esph.autoescola3esph.application.port.out;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrutor;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InstrutorRepository {
    Page<Instrutor> listarAtivos(Pageable paginacao);

    Instrutor escolherInstrutorAleatorioDisponivel(Especialidade especialidade, LocalDateTime dataHora);

    boolean existeInativoComId(Long id);

    boolean existePorEmail(String email);

    boolean existePorCnh(String cnh);

    Instrutor salvar(Instrutor instrutor);

    Optional<Instrutor> buscarPorId(Long id);

    boolean existePorId(Long id);
}

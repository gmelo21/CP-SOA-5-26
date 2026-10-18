package br.com.fiap3esph.autoescola3esph.application.port.out;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InstrucaoRepository {
    Instrucao salvar(Instrucao instrucao);

    Optional<Instrucao> buscarPorId(Long id);

    Page<Instrucao> listar(Pageable paginacao);

    boolean existeInstrutorOcupadoEm(Long idInstrutor, LocalDateTime dataHora);

    long contarAgendamentosDoAlunoNoDia(Long idAluno, LocalDateTime inicio, LocalDateTime fim);
}

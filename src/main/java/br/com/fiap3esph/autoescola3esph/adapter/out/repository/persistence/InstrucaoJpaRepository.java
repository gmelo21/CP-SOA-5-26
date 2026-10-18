package br.com.fiap3esph.autoescola3esph.adapter.out.repository.persistence;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.InstrucaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InstrucaoJpaRepository extends JpaRepository<InstrucaoEntity, Long> {
    boolean existsByInstrutorIdAndDataHoraAndMotivoCancelamentoIsNull(
            Long idInstrutor,
            LocalDateTime dataHora);

    @Query("""
            select count(i) from Instrucao i
            where
            i.aluno.id = :idAluno
            and
            i.dataHora between :inicio and :fim
            and
            i.motivoCancelamento is null
            """)
    long contarAgendamentosDoAlunoNoDia(
            Long idAluno,
            LocalDateTime inicio,
            LocalDateTime fim);
}

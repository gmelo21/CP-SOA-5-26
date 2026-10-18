package br.com.fiap3esph.autoescola3esph.adapter.out.repository;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.InstrucaoEntity;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.mapper.InstrucaoEntityMapper;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.persistence.AlunoJpaRepository;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.persistence.InstrucaoJpaRepository;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.persistence.InstrutorJpaRepository;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrucaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InstrucaoRepositoryImpl implements InstrucaoRepository {
    private final InstrucaoJpaRepository jpaRepository;
    private final AlunoJpaRepository alunoJpaRepository;
    private final InstrutorJpaRepository instrutorJpaRepository;
    private final InstrucaoEntityMapper entityMapper;

    @Override
    public Instrucao salvar(Instrucao instrucao) {
        InstrucaoEntity entity = new InstrucaoEntity(
                instrucao.getId(),
                alunoJpaRepository.getReferenceById(instrucao.getAluno().getId()),
                instrutorJpaRepository.getReferenceById(instrucao.getInstrutor().getId()),
                instrucao.getDataHora(),
                instrucao.getMotivoCancelamento(),
                instrucao.getDataCancelamento()
        );
        InstrucaoEntity saved = jpaRepository.save(entity);

        return new Instrucao(
                saved.getId(),
                instrucao.getAluno(),
                instrucao.getInstrutor(),
                saved.getDataHora(),
                saved.getMotivoCancelamento(),
                saved.getDataCancelamento()
        );
    }

    @Override
    public Optional<Instrucao> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public Page<Instrucao> listar(Pageable paginacao) {
        return jpaRepository.findAll(paginacao).map(entityMapper::toDomain);
    }

    @Override
    public boolean existeInstrutorOcupadoEm(Long idInstrutor, LocalDateTime dataHora) {
        if (idInstrutor == null) {
            return false;
        }
        return jpaRepository
                .existsByInstrutorIdAndDataHoraAndMotivoCancelamentoIsNull(idInstrutor, dataHora);
    }

    @Override
    public long contarAgendamentosDoAlunoNoDia(
            Long idAluno,
            LocalDateTime inicio,
            LocalDateTime fim) {
        return jpaRepository.contarAgendamentosDoAlunoNoDia(idAluno, inicio, fim);
    }
}

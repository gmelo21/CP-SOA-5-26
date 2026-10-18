package br.com.fiap3esph.autoescola3esph.adapter.out.repository.mapper;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.InstrucaoEntity;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrucao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstrucaoEntityMapper {
    private final AlunoEntityMapper alunoEntityMapper;
    private final InstrutorEntityMapper instrutorEntityMapper;

    public Instrucao toDomain(InstrucaoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Instrucao(
                entity.getId(),
                alunoEntityMapper.toDomain(entity.getAluno()),
                instrutorEntityMapper.toDomain(entity.getInstrutor()),
                entity.getDataHora(),
                entity.getMotivoCancelamento(),
                entity.getDataCancelamento()
        );
    }

    public InstrucaoEntity toEntity(Instrucao domain) {
        if (domain == null) {
            return null;
        }
        return new InstrucaoEntity(
                domain.getId(),
                alunoEntityMapper.toEntity(domain.getAluno()),
                instrutorEntityMapper.toEntity(domain.getInstrutor()),
                domain.getDataHora(),
                domain.getMotivoCancelamento(),
                domain.getDataCancelamento()
        );
    }
}

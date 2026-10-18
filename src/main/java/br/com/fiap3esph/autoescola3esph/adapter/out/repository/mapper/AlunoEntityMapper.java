package br.com.fiap3esph.autoescola3esph.adapter.out.repository.mapper;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.AlunoEntity;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoEntityMapper {
    public Aluno toDomain(AlunoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Aluno(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getTelefone(),
                entity.getCpf(),
                entity.getEndereco(),
                entity.isAtivo()
        );
    }

    public AlunoEntity toEntity(Aluno domain) {
        if (domain == null) {
            return null;
        }
        return new AlunoEntity(
                domain.getId(),
                domain.getNome(),
                domain.getEmail(),
                domain.getTelefone(),
                domain.getCpf(),
                domain.getEndereco(),
                domain.isAtivo()
        );
    }
}

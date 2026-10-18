package br.com.fiap3esph.autoescola3esph.adapter.out.repository;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.AlunoEntity;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.mapper.AlunoEntityMapper;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.persistence.AlunoJpaRepository;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Aluno;
import br.com.fiap3esph.autoescola3esph.application.port.out.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlunoRepositoryImpl implements AlunoRepository {
    private final AlunoJpaRepository jpaRepository;
    private final AlunoEntityMapper entityMapper;

    @Override
    public Page<Aluno> listarAtivos(Pageable paginacao) {
        return jpaRepository.findAllByAtivoTrue(paginacao).map(entityMapper::toDomain);
    }

    @Override
    public boolean existeInativoComId(Long id) {
        return id != null && jpaRepository.existsByIdAndAtivoFalse(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existePorId(Long id) {
        return id != null && jpaRepository.existsById(id);
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        AlunoEntity entity = entityMapper.toEntity(aluno);
        AlunoEntity saved = jpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public Optional<Aluno> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }
}

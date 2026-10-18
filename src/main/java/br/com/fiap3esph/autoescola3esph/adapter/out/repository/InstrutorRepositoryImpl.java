package br.com.fiap3esph.autoescola3esph.adapter.out.repository;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.InstrutorEntity;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.mapper.InstrutorEntityMapper;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.persistence.InstrutorJpaRepository;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrutor;
import br.com.fiap3esph.autoescola3esph.application.port.out.InstrutorRepository;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class InstrutorRepositoryImpl implements InstrutorRepository {
    private final InstrutorJpaRepository jpaRepository;
    private final InstrutorEntityMapper entityMapper;

    @Override
    public Page<Instrutor> listarAtivos(Pageable paginacao) {
        return jpaRepository.findAllByAtivoTrue(paginacao).map(entityMapper::toDomain);
    }

    @Override
    public Instrutor escolherInstrutorAleatorioDisponivel(
            Especialidade especialidade,
            LocalDateTime dataHora) {
        List<InstrutorEntity> disponiveis = jpaRepository.buscarDisponiveis(especialidade, dataHora);
        if (disponiveis.isEmpty()) {
            return null;
        }
        int sorteado = ThreadLocalRandom.current().nextInt(disponiveis.size());
        return entityMapper.toDomain(disponiveis.get(sorteado));
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
    public boolean existePorCnh(String cnh) {
        return jpaRepository.existsByCnh(cnh);
    }

    @Override
    public Instrutor salvar(Instrutor instrutor) {
        InstrutorEntity entity = entityMapper.toEntity(instrutor);
        InstrutorEntity saved = jpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public Optional<Instrutor> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public boolean existePorId(Long id) {
        return id != null && jpaRepository.existsById(id);
    }
}

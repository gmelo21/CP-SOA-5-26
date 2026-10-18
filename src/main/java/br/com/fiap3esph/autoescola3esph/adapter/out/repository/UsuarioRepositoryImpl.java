package br.com.fiap3esph.autoescola3esph.adapter.out.repository;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.UsuarioEntity;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.mapper.UsuarioEntityMapper;
import br.com.fiap3esph.autoescola3esph.adapter.out.repository.persistence.UsuarioJpaRepository;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import br.com.fiap3esph.autoescola3esph.application.port.out.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioEntityMapper entityMapper;

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        return jpaRepository.findByLogin(login).map(entityMapper::toDomain);
    }

    @Override
    public boolean existePorLogin(String login) {
        return jpaRepository.existsByLogin(login);
    }

    @Override
    public Page<Usuario> listar(Pageable paginacao) {
        return jpaRepository.findAll(paginacao).map(entityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = entityMapper.toEntity(usuario);
        UsuarioEntity saved = jpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public void excluirPorId(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return id != null && jpaRepository.existsById(id);
    }
}

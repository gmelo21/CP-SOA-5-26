package br.com.fiap3esph.autoescola3esph.adapter.out.repository.mapper;

import br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity.UsuarioEntity;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {
    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Usuario(
                entity.getId(),
                entity.getLogin(),
                entity.getSenha(),
                entity.getPerfil()
        );
    }

    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) {
            return null;
        }
        return new UsuarioEntity(
                domain.getId(),
                domain.getLogin(),
                domain.getSenha(),
                domain.getPerfil()
        );
    }
}

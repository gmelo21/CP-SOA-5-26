package br.com.fiap3esph.autoescola3esph.adapter.in.controller.mapper;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.usuario.DadosListagemUsuario;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public DadosDetalhamentoUsuario toDetails(Usuario usuario) {
        return new DadosDetalhamentoUsuario(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }

    public DadosListagemUsuario toList(Usuario usuario) {
        return new DadosListagemUsuario(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }
}

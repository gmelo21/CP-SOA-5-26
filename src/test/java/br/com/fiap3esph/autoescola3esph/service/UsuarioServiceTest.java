package br.com.fiap3esph.autoescola3esph.service;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.mapper.UsuarioMapper;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import br.com.fiap3esph.autoescola3esph.application.port.out.UsuarioRepository;
import br.com.fiap3esph.autoescola3esph.application.service.UsuarioService;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Perfil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Serviço de usuários")
class UsuarioServiceTest {
    @Test
    @DisplayName("Armazena apenas o hash BCrypt da senha no cadastro.")
    void cadastrarCenario1() {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        UsuarioService service = new UsuarioService(repository, new UsuarioMapper(), encoder);

        when(repository.existePorLogin("novo")).thenReturn(false);
        when(repository.salvar(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        service.cadastrarUsuario(new DadosCadastroUsuario("novo", "senhaSegura1", Perfil.USER));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).salvar(captor.capture());
        Usuario salvo = captor.getValue();

        assertThat(salvo.getSenha()).isNotEqualTo("senhaSegura1");
        assertThat(encoder.matches("senhaSegura1", salvo.getSenha())).isTrue();
    }
}

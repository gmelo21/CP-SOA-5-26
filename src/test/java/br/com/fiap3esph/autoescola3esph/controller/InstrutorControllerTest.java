package br.com.fiap3esph.autoescola3esph.controller;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3esph.autoescola3esph.application.service.InstrutorService;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.dto.DadosEndereco;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Endpoints de instrutores")
class InstrutorControllerTest {
    private static final String CORPO_VALIDO = """
            {
              "nome": "Instrutor Teste",
              "email": "instrutor.teste@email.com.br",
              "telefone": "(11) 91234-5678",
              "cnh": "01234567890",
              "especialidade": "CARROS",
              "endereco": { "cep": "01311-000", "numero": "1106" }
            }
            """;

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    InstrutorService service;

    @Test
    @DisplayName("POST /instrutores devolve 201 para um administrador.")
    @WithMockUser(roles = "ADMIN")
    void cadastrarCenario1() throws Exception {
        when(service.cadastrarInstrutor(any())).thenReturn(new DadosDetalhamentoInstrutor(
                1L,
                "Instrutor Teste",
                "instrutor.teste@email.com.br",
                "(11) 91234-5678",
                "01234567890",
                Especialidade.CARROS,
                new DadosEndereco("Avenida Paulista", "1106", null,
                        "Bela Vista", "São Paulo", "SP", "01311-000"),
                true));

        MockHttpServletResponse response = mockMvc
                .perform(post("/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CORPO_VALIDO))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).endsWith("/instrutores/1");
    }

    @Test
    @DisplayName("POST /instrutores devolve 403 para um usuário sem perfil de administrador.")
    @WithMockUser(roles = "USER")
    void cadastrarCenario2() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(post("/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CORPO_VALIDO))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}

package br.com.fiap3esph.autoescola3esph.controller;

import br.com.fiap3esph.autoescola3esph.application.service.AlunoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Endpoints de alunos")
class AlunoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AlunoService service;

    @Test
    @DisplayName("POST /alunos devolve 400 quando o CPF está fora do formato.")
    @WithMockUser(roles = "ADMIN")
    void cadastrarCenario1() throws Exception {
        String corpo = """
                {
                  "nome": "Aluno Teste",
                  "email": "aluno.teste@email.com.br",
                  "telefone": "(11) 98765-4321",
                  "cpf": "123",
                  "endereco": { "cep": "01311-000" }
                }
                """;

        MockHttpServletResponse response = mockMvc
                .perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corpo))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("cpf");
    }
}

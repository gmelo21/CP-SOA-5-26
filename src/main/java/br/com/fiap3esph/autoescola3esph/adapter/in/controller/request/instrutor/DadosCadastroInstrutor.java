package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrutor;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.dto.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroInstrutor(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "[0-9]{9,11}")
        String cnh,

        @NotNull
        Especialidade especialidade,

        @NotNull
        @Valid
        DadosEndereco endereco) {
}

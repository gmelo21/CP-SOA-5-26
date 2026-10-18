package br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.aluno;

import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.dto.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroAluno(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "deve conter 11 dígitos")
        String cpf,

        @NotNull
        @Valid
        DadosEndereco endereco) {
}

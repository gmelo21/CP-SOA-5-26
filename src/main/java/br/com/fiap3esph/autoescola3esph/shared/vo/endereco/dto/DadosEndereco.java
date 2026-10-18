package br.com.fiap3esph.autoescola3esph.shared.vo.endereco.dto;

import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @Schema(example = "Avenida Paulista")
        String logradouro,

        @Schema(example = "1106")
        String numero,

        @Schema(example = "Apto 72")
        String complemento,

        @Schema(example = "Bela Vista")
        String bairro,

        @Schema(example = "São Paulo")
        String cidade,

        @Pattern(regexp = "[A-Z]{2}", message = "deve conter 2 letras maiúsculas")
        @Schema(example = "SP")
        String uf,

        @NotBlank
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "deve seguir o formato 00000-000")
        @Schema(example = "01311-000")
        String cep) {

    public DadosEndereco(Endereco endereco) {
        this(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep()
        );
    }
}

package br.com.fiap3esph.autoescola3esph.adapter.out.integration.viacep.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ViaCepResposta(
        String cep,
        String logradouro,
        String complemento,
        String bairro,

        @JsonProperty("localidade")
        String cidade,
        String uf,
        Boolean erro) {

    public boolean cepInexistente() {
        return Boolean.TRUE.equals(erro);
    }
}

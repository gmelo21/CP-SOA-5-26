package br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.endereco;

import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;

public record DadosConsultaCep(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String cidade,
        String uf) {

    public DadosConsultaCep(Endereco endereco) {
        this(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf()
        );
    }
}

package br.com.fiap3esph.autoescola3esph.application.port.out;

import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;

public interface EnderecoGateway {
    Endereco buscarPorCep(String cep);
}

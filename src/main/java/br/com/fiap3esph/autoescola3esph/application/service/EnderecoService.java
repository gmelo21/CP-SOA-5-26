package br.com.fiap3esph.autoescola3esph.application.service;

import br.com.fiap3esph.autoescola3esph.application.port.out.EnderecoGateway;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoGateway gateway;

    public Endereco consultarPorCep(String cep) {
        return gateway.buscarPorCep(cep);
    }

    public Endereco completar(Endereco informado) {
        if (informado == null || vazio(informado.getCep())) {
            throw new ValidacaoException("O CEP é obrigatório para o cadastro do endereço.");
        }

        Endereco consultado = gateway.buscarPorCep(informado.getCep());

        Endereco completo = new Endereco(
                preferir(informado.getLogradouro(), consultado.getLogradouro()),
                informado.getNumero(),
                preferir(informado.getComplemento(), consultado.getComplemento()),
                preferir(informado.getBairro(), consultado.getBairro()),
                preferir(informado.getCidade(), consultado.getCidade()),
                preferir(informado.getUf(), consultado.getUf()),
                consultado.getCep()
        );

        if (vazio(completo.getLogradouro())
                || vazio(completo.getBairro())
                || vazio(completo.getCidade())
                || vazio(completo.getUf())) {
            throw new ValidacaoException(
                    "O CEP informado não retornou um endereço completo. "
                            + "Preencha logradouro, bairro, cidade e UF manualmente.");
        }

        return completo;
    }

    private String preferir(String informado, String consultado) {
        return vazio(informado) ? consultado : informado;
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }
}

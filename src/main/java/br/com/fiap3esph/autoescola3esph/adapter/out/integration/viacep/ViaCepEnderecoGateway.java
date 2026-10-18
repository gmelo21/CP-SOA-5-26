package br.com.fiap3esph.autoescola3esph.adapter.out.integration.viacep;

import br.com.fiap3esph.autoescola3esph.adapter.out.integration.viacep.dto.ViaCepResposta;
import br.com.fiap3esph.autoescola3esph.application.port.out.EnderecoGateway;
import br.com.fiap3esph.autoescola3esph.exception.type.IntegracaoExternaException;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ViaCepEnderecoGateway implements EnderecoGateway {
    private static final Logger log = LoggerFactory.getLogger(ViaCepEnderecoGateway.class);
    private static final int TAMANHO_DO_CEP = 8;

    private final RestClient.Builder construtor;
    private final String urlBase;
    private volatile RestClient clienteHttp;

    public ViaCepEnderecoGateway(
            RestClient.Builder construtor,
            @Value("${api.integracao.viacep.url:https://viacep.com.br/ws}") String urlBase) {
        this.construtor = construtor;
        this.urlBase = urlBase;
    }

    @Override
    public Endereco buscarPorCep(String cep) {
        String cepNumerico = somenteDigitos(cep);
        if (cepNumerico.length() != TAMANHO_DO_CEP) {
            throw new IntegracaoExternaException("CEP inválido: " + cep);
        }

        ViaCepResposta resposta;
        try {
            resposta = clienteHttp()
                    .get()
                    .uri("/{cep}/json/", cepNumerico)
                    .retrieve()
                    .body(ViaCepResposta.class);
        } catch (RestClientException ex) {
            log.warn("Falha ao consultar o ViaCEP para o CEP {}.", cepNumerico, ex);
            throw new IntegracaoExternaException(
                    "Não foi possível consultar o serviço de CEP no momento.");
        }

        if (resposta == null || resposta.cepInexistente()) {
            throw new IntegracaoExternaException("CEP não encontrado: " + cep);
        }

        return new Endereco(
                resposta.logradouro(),
                null,
                resposta.complemento(),
                resposta.bairro(),
                resposta.cidade(),
                resposta.uf(),
                formatar(cepNumerico)
        );
    }

    private RestClient clienteHttp() {
        RestClient instancia = clienteHttp;
        if (instancia == null) {
            synchronized (this) {
                instancia = clienteHttp;
                if (instancia == null) {
                    instancia = construtor.baseUrl(urlBase).build();
                    clienteHttp = instancia;
                }
            }
        }
        return instancia;
    }

    private String somenteDigitos(String cep) {
        return cep == null ? "" : cep.replaceAll("[^0-9]", "");
    }

    private String formatar(String cepNumerico) {
        return cepNumerico.substring(0, 5) + "-" + cepNumerico.substring(5);
    }
}

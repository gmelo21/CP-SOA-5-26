package br.com.fiap3esph.autoescola3esph.adapter.in.controller.mapper;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrutor.DadosCadastroInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor.DadosListagemInstrutor;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Instrutor;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.mapper.EnderecoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstrutorMapper {
    private final EnderecoMapper enderecoMapper;

    public Instrutor toDomain(DadosCadastroInstrutor dados, Endereco endereco) {
        return new Instrutor(
                null,
                dados.nome(),
                dados.email(),
                dados.telefone(),
                dados.cnh(),
                dados.especialidade(),
                endereco,
                true
        );
    }

    public DadosDetalhamentoInstrutor toDetails(Instrutor instrutor) {
        return new DadosDetalhamentoInstrutor(
                instrutor.getId(),
                instrutor.getNome(),
                instrutor.getEmail(),
                instrutor.getTelefone(),
                instrutor.getCnh(),
                instrutor.getEspecialidade(),
                enderecoMapper.toDto(instrutor.getEndereco()),
                instrutor.isAtivo()
        );
    }

    public DadosListagemInstrutor toList(Instrutor instrutor) {
        return new DadosListagemInstrutor(
                instrutor.getId(),
                instrutor.getNome(),
                instrutor.getEmail(),
                instrutor.getCnh(),
                instrutor.getEspecialidade()
        );
    }
}

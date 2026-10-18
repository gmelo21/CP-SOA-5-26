package br.com.fiap3esph.autoescola3esph.adapter.in.controller.mapper;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.aluno.DadosCadastroAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.aluno.DadosDetalhamentoAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.aluno.DadosListagemAluno;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Aluno;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.mapper.EnderecoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlunoMapper {
    private final EnderecoMapper enderecoMapper;

    public Aluno toDomain(DadosCadastroAluno dados, Endereco endereco) {
        return new Aluno(
                null,
                dados.nome(),
                dados.email(),
                dados.telefone(),
                dados.cpf(),
                endereco,
                true
        );
    }

    public DadosDetalhamentoAluno toDetails(Aluno aluno) {
        return new DadosDetalhamentoAluno(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getCpf(),
                enderecoMapper.toDto(aluno.getEndereco()),
                aluno.isAtivo()
        );
    }

    public DadosListagemAluno toList(Aluno aluno) {
        return new DadosListagemAluno(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCpf()
        );
    }
}

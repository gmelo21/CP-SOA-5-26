package br.com.fiap3esph.autoescola3esph.application.core.domain;

import br.com.fiap3esph.autoescola3esph.shared.vo.endereco.Endereco;
import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.Especialidade;

public class Instrutor {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cnh;
    private Especialidade especialidade;
    private Endereco endereco;
    private boolean ativo = true;

    public Instrutor() {
    }

    public Instrutor(
            Long id,
            String nome,
            String email,
            String telefone,
            String cnh,
            Especialidade especialidade,
            Endereco endereco,
            boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cnh = cnh;
        this.especialidade = especialidade;
        this.endereco = endereco;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCnh() {
        return cnh;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void atualizarInformacoes(
            String nome,
            String telefone,
            Endereco endereco) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
        if (telefone != null && !telefone.isBlank()) {
            this.telefone = telefone;
        }
        if (endereco != null) {
            if (this.endereco == null) {
                this.endereco = endereco;
            } else {
                this.endereco.atualizarInformacoes(
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
    }

    public void excluir() {
        this.ativo = false;
    }
}

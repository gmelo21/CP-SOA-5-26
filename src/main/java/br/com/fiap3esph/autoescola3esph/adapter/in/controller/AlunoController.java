package br.com.fiap3esph.autoescola3esph.adapter.in.controller;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.aluno.DadosAtualizacaoAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.aluno.DadosCadastroAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.aluno.DadosDetalhamentoAluno;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.aluno.DadosListagemAluno;
import br.com.fiap3esph.autoescola3esph.application.port.in.PortaCrudPadrao;
import br.com.fiap3esph.autoescola3esph.application.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/alunos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Alunos", description = "Cadastro e gerenciamento dos alunos da auto-escola")
@RequiredArgsConstructor
public class AlunoController implements PortaCrudPadrao<
        DadosCadastroAluno,
        DadosListagemAluno,
        DadosAtualizacaoAluno,
        Void,
        DadosDetalhamentoAluno,
        Long
        > {
    private final AlunoService service;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra um novo aluno (endereço completado via ViaCEP)")
    public ResponseEntity<DadosDetalhamentoAluno> cadastrar(
            @RequestBody @Valid DadosCadastroAluno dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoAluno dto = service.cadastrarAluno(dados);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Lista os alunos ativos, ordenados por nome, 10 por página")
    public ResponseEntity<Page<DadosListagemAluno>> listar(
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        return ResponseEntity.ok(service.listarAlunos(paginacao));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Detalha um aluno pelo id")
    public ResponseEntity<DadosDetalhamentoAluno> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(service.detalharAluno(id));
    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza nome, telefone e endereço do aluno (e-mail e CPF são imutáveis)")
    public ResponseEntity<DadosDetalhamentoAluno> atualizar(
            @RequestBody @Valid DadosAtualizacaoAluno dados) {
        return ResponseEntity.ok(service.atualizarAluno(dados));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclusão lógica: inativa o aluno sem apagar seus dados")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirAluno(id);
        return ResponseEntity.noContent().build();
    }
}

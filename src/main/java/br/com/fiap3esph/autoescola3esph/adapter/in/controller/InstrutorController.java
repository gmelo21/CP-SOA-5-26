package br.com.fiap3esph.autoescola3esph.adapter.in.controller;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrutor.DadosAtualizacaoInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrutor.DadosCadastroInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrutor.DadosListagemInstrutor;
import br.com.fiap3esph.autoescola3esph.application.port.in.PortaCrudPadrao;
import br.com.fiap3esph.autoescola3esph.application.service.InstrutorService;
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
@RequestMapping("/instrutores")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Instrutores", description = "Cadastro e gerenciamento dos instrutores da auto-escola")
@RequiredArgsConstructor
public class InstrutorController implements PortaCrudPadrao<
        DadosCadastroInstrutor,
        DadosListagemInstrutor,
        DadosAtualizacaoInstrutor,
        Void,
        DadosDetalhamentoInstrutor,
        Long
        > {
    private final InstrutorService service;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra um novo instrutor (endereço completado via ViaCEP)")
    public ResponseEntity<DadosDetalhamentoInstrutor> cadastrar(
            @RequestBody @Valid DadosCadastroInstrutor dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoInstrutor dto = service.cadastrarInstrutor(dados);
        URI uri = uriBuilder.path("/instrutores/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Lista os instrutores ativos, ordenados por nome, 10 por página")
    public ResponseEntity<Page<DadosListagemInstrutor>> listar(
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        return ResponseEntity.ok(service.listarInstrutores(paginacao));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Detalha um instrutor pelo id")
    public ResponseEntity<DadosDetalhamentoInstrutor> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(service.detalharInstrutor(id));
    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza nome, telefone e endereço (e-mail, CNH e especialidade são imutáveis)")
    public ResponseEntity<DadosDetalhamentoInstrutor> atualizar(
            @RequestBody @Valid DadosAtualizacaoInstrutor dados) {
        return ResponseEntity.ok(service.atualizarInstrutor(dados));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclusão lógica: inativa o instrutor sem apagar seus dados")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirInstrutor(id);
        return ResponseEntity.noContent().build();
    }
}

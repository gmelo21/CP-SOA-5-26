package br.com.fiap3esph.autoescola3esph.adapter.in.controller;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosAlteracaoSenha;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosAtualizacaoUsuario;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.usuario.DadosListagemUsuario;
import br.com.fiap3esph.autoescola3esph.application.service.UsuarioService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuários", description = "Gerenciamento de usuários (restrito a administradores)")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra um usuário; a senha é armazenada apenas como hash BCrypt")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoUsuario dto = service.cadastrarUsuario(dados);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista os usuários cadastrados, 10 por página")
    public ResponseEntity<Page<DadosListagemUsuario>> listar(
            @ParameterObject @PageableDefault(size = 10, sort = "login") Pageable paginacao) {
        return ResponseEntity.ok(service.listarUsuarios(paginacao));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Detalha um usuário pelo id")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(service.detalharUsuario(id));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza o perfil (ADMIN/USER) de um usuário")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarPerfil(
            @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        return ResponseEntity.ok(service.atualizarPerfil(dados));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Exclui um usuário")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/senha")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Permite que o usuário autenticado altere a própria senha")
    public ResponseEntity<Void> alterarPropriaSenha(
            @AuthenticationPrincipal UserDetails autenticado,
            @RequestBody @Valid DadosAlteracaoSenha dados) {
        service.alterarPropriaSenha(autenticado.getUsername(), dados);
        return ResponseEntity.noContent().build();
    }
}

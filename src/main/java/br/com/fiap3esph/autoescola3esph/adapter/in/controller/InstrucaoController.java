package br.com.fiap3esph.autoescola3esph.adapter.in.controller;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.instrucao.DadosCancelamento;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrucao.DadosListagemInstrucao;
import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.instrucao.DetalhamentoAgendamento;
import br.com.fiap3esph.autoescola3esph.application.service.AgendaDeInstrucoes;
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
@RequestMapping("/instrucoes")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Instruções", description = "Agendamento e cancelamento de instruções")
@RequiredArgsConstructor
public class InstrucaoController {
    private final AgendaDeInstrucoes agenda;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Agenda uma instrução; o instrutor é sorteado quando não informado")
    public ResponseEntity<DetalhamentoAgendamento> agendar(
            @RequestBody @Valid DadosAgendamento dados,
            UriComponentsBuilder uriBuilder) {
        DetalhamentoAgendamento dto = agenda.agendar(dados);
        URI uri = uriBuilder.path("/instrucoes/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Cancela uma instrução com antecedência mínima de 24 horas")
    public ResponseEntity<DetalhamentoAgendamento> cancelar(
            @RequestBody @Valid DadosCancelamento dados) {
        return ResponseEntity.ok(agenda.cancelar(dados));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Lista as instruções agendadas, 10 por página")
    public ResponseEntity<Page<DadosListagemInstrucao>> listar(
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora") Pageable paginacao) {
        return ResponseEntity.ok(agenda.listar(paginacao));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Detalha uma instrução pelo id")
    public ResponseEntity<DetalhamentoAgendamento> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(agenda.detalhar(id));
    }
}

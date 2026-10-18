package br.com.fiap3esph.autoescola3esph.adapter.in.controller;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.response.endereco.DadosConsultaCep;
import br.com.fiap3esph.autoescola3esph.application.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Endereços", description = "Consulta de endereços no WebService externo ViaCEP")
@RequiredArgsConstructor
public class EnderecoController {
    private final EnderecoService service;

    @GetMapping("/{cep}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Consulta um endereço pelo CEP, no serviço externo ViaCEP")
    public ResponseEntity<DadosConsultaCep> consultarPorCep(@PathVariable String cep) {
        return ResponseEntity.ok(new DadosConsultaCep(service.consultarPorCep(cep)));
    }
}

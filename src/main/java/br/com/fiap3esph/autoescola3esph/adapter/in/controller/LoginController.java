package br.com.fiap3esph.autoescola3esph.adapter.in.controller;

import br.com.fiap3esph.autoescola3esph.adapter.in.controller.request.usuario.DadosLogin;
import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import br.com.fiap3esph.autoescola3esph.config.security.service.ServicoDeToken;
import br.com.fiap3esph.autoescola3esph.config.security.service.dto.DadosTokenJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Emissão do token JWT de acesso à API")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager manager;
    private final ServicoDeToken servicoDeToken;

    @PostMapping
    @Operation(summary = "Autentica um usuário e devolve o token JWT")
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosLogin dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication authentication = manager.authenticate(token);
        String tokenJWT = servicoDeToken.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}

package br.com.fiap3esph.autoescola3esph.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TratadorDeAcessoNegado implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        RespostaDeErroDeSeguranca.escrever(
                response,
                HttpStatus.FORBIDDEN,
                "Acesso negado: perfil sem permissão para esta operação.");
    }
}

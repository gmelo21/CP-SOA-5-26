package br.com.fiap3esph.autoescola3esph.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TratadorDeNaoAutenticado implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        RespostaDeErroDeSeguranca.escrever(
                response,
                HttpStatus.UNAUTHORIZED,
                "Autenticação necessária: informe um token JWT válido em POST /login.");
    }
}

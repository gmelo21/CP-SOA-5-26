package br.com.fiap3esph.autoescola3esph.config.security.filter;

import br.com.fiap3esph.autoescola3esph.application.port.out.UsuarioRepository;
import br.com.fiap3esph.autoescola3esph.config.security.service.ServicoDeToken;
import br.com.fiap3esph.autoescola3esph.exception.type.TokenInvalidoException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FiltroDeSeguranca extends OncePerRequestFilter {
    private final ServicoDeToken servicoDeToken;
    private final UsuarioRepository repository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                String subject = servicoDeToken.extrairLogin(tokenJWT);
                Optional<? extends UserDetails> usuario = repository.buscarPorLogin(subject);
                usuario.ifPresent(details -> {
                    var authenticated = new UsernamePasswordAuthenticationToken(
                            details,
                            null,
                            details.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticated);
                });
            } catch (TokenInvalidoException ex) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring("Bearer ".length()).trim();
        }
        return null;
    }
}

package br.com.fiap3esph.autoescola3esph.config.security.service;

import br.com.fiap3esph.autoescola3esph.application.core.domain.Usuario;
import br.com.fiap3esph.autoescola3esph.exception.type.TokenInvalidoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class ServicoDeToken {
    private static final String EMISSOR = "AutoEscola3ESPH";

    @Value("${api.security.token.secret}")
    private String segredo;

    @Value("${api.security.token.expiration-minutes:120}")
    private long expiracaoEmMinutos;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(segredo);
            return JWT.create()
                    .withIssuer(EMISSOR)
                    .withSubject(usuario.getLogin())
                    .withClaim("perfil", usuario.getPerfil().name())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new IllegalStateException("Erro ao gerar o token JWT!", ex);
        }
    }

    public String extrairLogin(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(segredo);
            return JWT.require(algorithm)
                    .withIssuer(EMISSOR)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new TokenInvalidoException("Token JWT inválido ou expirado!", ex);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusMinutes(expiracaoEmMinutos).toInstant(ZoneOffset.of("-03:00"));
    }
}

package br.com.fiap3esph.autoescola3esph.exception;

import br.com.fiap3esph.autoescola3esph.exception.type.AlunoNaoEncontradoException;
import br.com.fiap3esph.autoescola3esph.exception.type.InstrucaoNaoEncontradaException;
import br.com.fiap3esph.autoescola3esph.exception.type.InstrutorNaoEncontradoException;
import br.com.fiap3esph.autoescola3esph.exception.type.IntegracaoExternaException;
import br.com.fiap3esph.autoescola3esph.exception.type.TokenInvalidoException;
import br.com.fiap3esph.autoescola3esph.exception.type.UsuarioNaoEncontradoException;
import br.com.fiap3esph.autoescola3esph.exception.type.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeExcecoes {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarGenericNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosBadRequest>> tratarBadRequest(MethodArgumentNotValidException e) {
        List<FieldError> erros = e.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosBadRequest::new).toList());
    }

    @ExceptionHandler({
            InstrutorNaoEncontradoException.class,
            AlunoNaoEncontradoException.class,
            UsuarioNaoEncontradoException.class,
            InstrucaoNaoEncontradaException.class
    })
    public ResponseEntity<DadosMessage> tratarNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<DadosMessage> tratarRegraDeNegocio(ValidacaoException e) {
        return ResponseEntity.badRequest().body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DadosMessage> tratarConflito(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new DadosMessage("Já existe um registro cadastrado com os dados informados."));
    }

    @ExceptionHandler(IntegracaoExternaException.class)
    public ResponseEntity<DadosMessage> tratarIntegracaoExterna(IntegracaoExternaException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DadosMessage> tratarAcessoNegado() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new DadosMessage("Acesso negado: perfil sem permissão para esta operação."));
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<DadosMessage> tratarTokenInvalido(TokenInvalidoException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new DadosMessage(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DadosMessage> tratarFalhaDeAutenticacao() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new DadosMessage("Credenciais inválidas."));
    }

    public record DadosBadRequest(String campo, String mensagem) {
        public DadosBadRequest(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    public record DadosMessage(String mensagem) {
    }
}

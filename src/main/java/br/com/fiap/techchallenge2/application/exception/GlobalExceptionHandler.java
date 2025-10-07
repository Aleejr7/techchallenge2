package br.com.fiap.techchallenge2.application.exception;

import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.DadoInvalidoException;
import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.disponibilidadePedido.DisponibilidadePedidoInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioJaExisteException;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteJaExisteException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioJaExisteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.SenhaErradaException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioJaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    // Exceções de Acesso - 403 FORBIDDEN
    @ExceptionHandler( AcessoNegadoException.class )
    public ResponseEntity<ErrorResponse> handleAcessoNegado( AcessoNegadoException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Acesso Negado",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.FORBIDDEN ).body( errorResponse );
    }

    // Exceções de Autenticação - 401 UNAUTHORIZED
    @ExceptionHandler( SenhaErradaException.class )
    public ResponseEntity<ErrorResponse> handleSenhaErrada( SenhaErradaException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Credenciais Inválidas",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( errorResponse );
    }

    // Exceções de Validação - 400 BAD REQUEST
    @ExceptionHandler( DadoInvalidoException.class )
    public ResponseEntity<ErrorResponse> handleDadoInvalido( DadoInvalidoException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Dado Inválido",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( errorResponse );
    }

    @ExceptionHandler( DadoVazioException.class )
    public ResponseEntity<ErrorResponse> handleDadoVazio( DadoVazioException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Dado Vazio",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( errorResponse );
    }

    // Exceções de Recurso Não Encontrado - 404 NOT FOUND
    @ExceptionHandler( CardapioInexistenteException.class )
    public ResponseEntity<ErrorResponse> handleCardapioInexistente( CardapioInexistenteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Cardápio Não Encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( errorResponse );
    }

    @ExceptionHandler( DisponibilidadePedidoInexistenteException.class )
    public ResponseEntity<ErrorResponse> handleDisponibilidadePedidoInexistente( DisponibilidadePedidoInexistenteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Disponibilidade de Pedido Não Encontrada",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( errorResponse );
    }

    @ExceptionHandler( ItemCardapioInexistenteException.class )
    public ResponseEntity<ErrorResponse> handleItemCardapioInexistente( ItemCardapioInexistenteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Item do Cardápio Não Encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( errorResponse );
    }

    @ExceptionHandler( RestauranteInexistenteException.class )
    public ResponseEntity<ErrorResponse> handleRestauranteInexistente( RestauranteInexistenteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Restaurante Não Encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( errorResponse );
    }

    @ExceptionHandler( TipoUsuarioInexistenteException.class )
    public ResponseEntity<ErrorResponse> handleTipoUsuarioInexistente( TipoUsuarioInexistenteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Tipo de Usuário Não Encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( errorResponse );
    }

    @ExceptionHandler( UsuarioInexistenteException.class )
    public ResponseEntity<ErrorResponse> handleUsuarioInexistente( UsuarioInexistenteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Usuário Não Encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( errorResponse );
    }

    // Exceções de Conflito - 409 CONFLICT
    @ExceptionHandler( ItemCardapioJaExisteException.class )
    public ResponseEntity<ErrorResponse> handleItemCardapioJaExiste( ItemCardapioJaExisteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Item do Cardápio Já Existe",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.CONFLICT ).body( errorResponse );
    }

    @ExceptionHandler( RestauranteJaExisteException.class )
    public ResponseEntity<ErrorResponse> handleRestauranteJaExiste( RestauranteJaExisteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Restaurante Já Existe",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.CONFLICT ).body( errorResponse );
    }

    @ExceptionHandler( TipoUsuarioJaExisteException.class )
    public ResponseEntity<ErrorResponse> handleTipoUsuarioJaExiste( TipoUsuarioJaExisteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Tipo de Usuário Já Existe",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.CONFLICT ).body( errorResponse );
    }

    @ExceptionHandler( UsuarioJaExisteException.class )
    public ResponseEntity<ErrorResponse> handleUsuarioJaExiste( UsuarioJaExisteException ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Usuário Já Existe",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.CONFLICT ).body( errorResponse );
    }

    // Exceção Genérica - 500 INTERNAL SERVER ERROR
    @ExceptionHandler( Exception.class )
    public ResponseEntity<ErrorResponse> handleGenericException( Exception ex )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                ex.getMessage()
        );
        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( errorResponse );
    }
}

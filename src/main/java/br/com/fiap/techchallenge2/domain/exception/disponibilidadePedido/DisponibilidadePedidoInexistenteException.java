package br.com.fiap.techchallenge2.domain.exception.disponibilidadePedido;

public class DisponibilidadePedidoInexistenteException extends RuntimeException
{
    public DisponibilidadePedidoInexistenteException( String message ) {
        super( message );
    }
}

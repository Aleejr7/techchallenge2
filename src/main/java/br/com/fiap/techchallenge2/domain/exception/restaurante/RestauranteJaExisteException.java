package br.com.fiap.techchallenge2.domain.exception.restaurante;

public class RestauranteJaExisteException extends RuntimeException
{
    public RestauranteJaExisteException( String message )
    {
        super( message );
    }
}

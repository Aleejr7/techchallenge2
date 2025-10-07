package br.com.fiap.techchallenge2.domain.exception.restaurante;

public class RestauranteInexistenteException extends RuntimeException
{
    public RestauranteInexistenteException( String message )
    {
        super( message );
    }
}

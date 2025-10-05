package br.com.fiap.techchallenge2.domain.exception.usuario;

public class SenhaErradaException extends RuntimeException
{
    public SenhaErradaException( String message )
    {
        super( message );
    }
}

package br.com.fiap.techchallenge2.domain.exception;

public class DadoInvalidoException extends RuntimeException
{
    public DadoInvalidoException( String message )
    {
        super( message );
    }
}

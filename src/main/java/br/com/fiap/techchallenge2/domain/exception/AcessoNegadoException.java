package br.com.fiap.techchallenge2.domain.exception;

public class AcessoNegadoException extends RuntimeException
{
    public AcessoNegadoException( String message )
    {
        super( message );
    }
}

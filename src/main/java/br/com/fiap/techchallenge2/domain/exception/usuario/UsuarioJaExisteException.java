package br.com.fiap.techchallenge2.domain.exception.usuario;

public class UsuarioJaExisteException extends RuntimeException
{
    public UsuarioJaExisteException( String message )
    {
        super( message );
    }
}

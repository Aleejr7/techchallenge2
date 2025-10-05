package br.com.fiap.techchallenge2.domain.exception.usuario;

public class UsuarioInexistenteException extends RuntimeException
{
    public UsuarioInexistenteException( String message )
    {
        super( message );
    }
}

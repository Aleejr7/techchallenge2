package br.com.fiap.techchallenge2.domain.exception.tipousuario;

public class TipoUsuarioJaExisteException extends RuntimeException
{
    public TipoUsuarioJaExisteException( String message ) {
        super( message );
    }
}

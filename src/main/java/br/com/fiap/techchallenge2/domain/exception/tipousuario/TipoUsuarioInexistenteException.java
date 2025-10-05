package br.com.fiap.techchallenge2.domain.exception.tipousuario;

public class TipoUsuarioInexistenteException extends RuntimeException
{
    public TipoUsuarioInexistenteException( String message ) {
        super( message );
    }
}

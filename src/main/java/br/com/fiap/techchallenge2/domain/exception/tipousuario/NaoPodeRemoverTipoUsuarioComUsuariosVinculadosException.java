package br.com.fiap.techchallenge2.domain.exception.tipousuario;

public class NaoPodeRemoverTipoUsuarioComUsuariosVinculadosException extends RuntimeException
{
    public NaoPodeRemoverTipoUsuarioComUsuariosVinculadosException(String message ) {
        super( message );
    }
}

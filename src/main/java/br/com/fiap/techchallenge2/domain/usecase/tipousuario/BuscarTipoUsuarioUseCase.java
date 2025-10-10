package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.BuscarTipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarTipoUsuarioUseCase
{
    private final TipoUsuarioInterface tipoUsuarioInterface;

    public TipoUsuarioOutput execute( BuscarTipoUsuarioInput tipoUsuarioInput ){

        if ( !tipoUsuarioInput.tipoUsuarioLogado( ).equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'Admin' podem buscar um tipo de usuário" );
        }

        TipoUsuario tipoUsuario = tipoUsuarioInterface.buscarTipoUsuarioPorUuid( tipoUsuarioInput.uuid( ) );
        if( tipoUsuario == null ){
            throw new TipoUsuarioInexistenteException( "O usuário com o UUID: " + tipoUsuarioInput.uuid( ) + " não existe" );
        }

        return new TipoUsuarioOutput( tipoUsuario.getId( ), tipoUsuario.getNome( ) );
    }
}

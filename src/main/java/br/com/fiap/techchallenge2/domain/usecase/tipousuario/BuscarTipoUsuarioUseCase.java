package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.TipoUsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarTipoUsuarioUseCase
{
    private final TipoUsuarioInterface tipoUsuarioInterface;

    public TipoUsuarioOutput execute( TipoUsuarioInput tipoUsuarioInput ){

        if ( !tipoUsuarioInput.tipoUsuarioLogado( ).equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'Admin' podem buscar um tipo de usuário" );
        }

        TipoUsuario tipoUsuario = tipoUsuarioInterface.buscarTipoUsuarioPorNome( tipoUsuarioInput.nome( ) );
        if( tipoUsuario == null ){
            throw new TipoUsuarioInexistenteException( "O usuário " + tipoUsuarioInput.nome( ) + " a ser buscado não existe" );
        }

        return new TipoUsuarioOutput( tipoUsuario.getId( ), tipoUsuario.getNome( ) );
    }
}

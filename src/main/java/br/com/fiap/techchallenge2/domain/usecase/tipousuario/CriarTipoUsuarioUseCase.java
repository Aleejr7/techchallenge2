package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.TipoUsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarTipoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;


    public TipoUsuarioOutput execute( TipoUsuarioInput tipoUsuarioInput ){

        TipoUsuario tipoUsuarioExistente = this.tipoUsuarioInterface.buscarTipoUsuarioPorNome( tipoUsuarioInput.nome( ) );
        if( tipoUsuarioExistente != null ){
            throw new TipoUsuarioJaExisteException( "O tipo de usuário " + tipoUsuarioInput.nome( ) + " já existe" );
        }

        TipoUsuario tipoUsuario = new TipoUsuario( tipoUsuarioInput.nome( ) );
        tipoUsuario =  this.tipoUsuarioInterface.criarTipoUsuario( tipoUsuario );

        return new TipoUsuarioOutput( tipoUsuario.getId( ), tipoUsuario.getNome( ) );
    }

}

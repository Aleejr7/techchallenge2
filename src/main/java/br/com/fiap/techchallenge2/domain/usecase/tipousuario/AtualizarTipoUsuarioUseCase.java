package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.AtualizarTipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarTipoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;

    public TipoUsuarioOutput execute( AtualizarTipoUsuarioInput tipoUsuarioInput ) {

        if ( !tipoUsuarioInput.tipoUsuarioLogado( ).equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'Admin' podem atualizar um tipo de usuário" );
        }

        TipoUsuario tipoUsuario = this.tipoUsuarioInterface.buscarTipoUsuarioPorNome( tipoUsuarioInput.nome( ) );
        if( tipoUsuario != null ){
            throw new TipoUsuarioJaExisteException( "Já existe um tipo usuário com o nome " + tipoUsuarioInput.nome( ) + ".");
        }

        TipoUsuario tipoUsuarioExistente = this.tipoUsuarioInterface.buscarTipoUsuarioPorUuid( tipoUsuarioInput.uuid() );
        if ( tipoUsuarioExistente == null ) {
            throw new TipoUsuarioInexistenteException( "Tipo usuário com o UUID: " + tipoUsuarioInput.uuid() + " não existe." );
        }

        tipoUsuarioExistente.setNome( tipoUsuarioInput.nome() );
        TipoUsuario tipoUsuarioAtualizado = this.tipoUsuarioInterface.atualizarTipoUsuario( tipoUsuarioExistente );

        return new TipoUsuarioOutput( tipoUsuarioAtualizado.getId(), tipoUsuarioAtualizado.getNome( ) );
    }
}

package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletarTipoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;


    public void execute( TipoUsuarioInput tipoUsuarioInput, String tipoUsuarioLogado ){

        if ( !tipoUsuarioLogado.equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'Admin' podem deletar um tipo de usuário" );
        }

        TipoUsuario tipoUsuario = this.tipoUsuarioInterface.buscarTipoUsuarioPorNome( tipoUsuarioInput.nome( ) );

        if( tipoUsuario == null ){
            throw new TipoUsuarioInexistenteException( "O tipo de usuário " + tipoUsuarioInput.nome( ) + " a ser apagado não existe" );
        }

        this.tipoUsuarioInterface.deletarTipoUsuarioPorNome( tipoUsuarioInput.nome( ) );
    }
}

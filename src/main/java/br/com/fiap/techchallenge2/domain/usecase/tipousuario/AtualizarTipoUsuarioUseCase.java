package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.TipoUsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarTipoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;

    public TipoUsuarioOutput execute( TipoUsuarioInput tipoUsuarioInput, String tipoUsuarioLogado ) {

        if ( !tipoUsuarioLogado.equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'Admin' podem atualizar um tipo de usuário" );
        }

        TipoUsuario tipoUsuario = this.tipoUsuarioInterface.buscarTipoUsuarioPorNome( tipoUsuarioInput.nome( ) );
        if( tipoUsuario == null ){
            throw new TipoUsuarioInexistenteException( "O tipo usuário " + tipoUsuarioInput.nome( ) + " a ser atualizado não existe");
        }

        tipoUsuario.setNome( tipoUsuarioInput.nome() );
        TipoUsuario tipoUsuarioAtualizado = this.tipoUsuarioInterface.atualizarTipoUsuario( tipoUsuario );

        return new TipoUsuarioOutput( tipoUsuarioAtualizado.getId(), tipoUsuarioAtualizado.getNome( ) );
    }
}

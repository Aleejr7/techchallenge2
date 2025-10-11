package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.NaoPodeRemoverTipoUsuarioComUsuariosVinculadosException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.DeletarTipoUsuarioInput;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeletarTipoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;
    private final UsuarioInterface usuarioInterface;

    public void execute( DeletarTipoUsuarioInput tipoUsuarioInput ){

        if ( !tipoUsuarioInput.tipoUsuarioLogado( ).equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'Admin' podem deletar um tipo de usuário" );
        }

        TipoUsuario tipoUsuario = this.tipoUsuarioInterface.buscarTipoUsuarioPorUuid( tipoUsuarioInput.uuid( ) );
        if( tipoUsuario == null ){
            throw new TipoUsuarioInexistenteException( "O tipo de usuário com o UUID: " + tipoUsuarioInput.uuid( ) + " a ser apagado não existe" );
        }

        List<Usuario> lista = this.usuarioInterface.buscarUsuariosPorTipo(tipoUsuario.getId());
        if ( !lista.isEmpty() ) {
            throw new NaoPodeRemoverTipoUsuarioComUsuariosVinculadosException( "O tipo de usuário com o UUID: " + tipoUsuarioInput.uuid( ) + " não pode ser removido porque existem usuários cadastrados com esse tipo" );
        }

        this.tipoUsuarioInterface.deletarTipoUsuarioPorUuid(tipoUsuario.getId());
    }
}

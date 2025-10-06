package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.AlterarTipoDoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.UsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlterarTipoDoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;
    private final UsuarioInterface usuarioInterface;

    public UsuarioOutput execute ( AlterarTipoDoUsuarioInput alterarTipoDoUsuarioInput, String tipoUsuarioLogado ) {

        if ( !tipoUsuarioLogado.equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários 'Admin' podem alterar o tipo de um usuário." );
        }

        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorUuid( alterarTipoDoUsuarioInput.uuidUsuario() );
        if ( usuarioExistente == null ) {
            throw new AcessoNegadoException( "Usuário com o UUID " + alterarTipoDoUsuarioInput.uuidUsuario() + " não existe." );
        }

        TipoUsuario tipoUsuarioExistente = this.tipoUsuarioInterface.buscarTipoUsuarioPorNome( alterarTipoDoUsuarioInput.tipoUsuarioInput( ).nome( ) );
        if ( tipoUsuarioExistente == null ) {
            throw new TipoUsuarioInexistenteException(
                    "O tipo usuário " + alterarTipoDoUsuarioInput.tipoUsuarioInput( ).nome( ) + " não existe, precisa cria-lo ou use um existente." );
        }

        usuarioExistente.setTipoUsuario( tipoUsuarioExistente );
        Usuario usuarioAtualizado = this.usuarioInterface.atualizarUsuario( usuarioExistente );

        return new UsuarioOutput(
                usuarioAtualizado.getUuid(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getCpf(),
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getTelefone(),
                usuarioAtualizado.getEndereco(),
                usuarioAtualizado.getTipoUsuario().getNome()
        );
    }
}

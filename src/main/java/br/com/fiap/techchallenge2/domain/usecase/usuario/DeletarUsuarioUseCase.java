package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeletarUsuarioUseCase
{
    private final UsuarioInterface usuarioInterface;

    public void execute( UUID uuid )
    {
        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorUuid( uuid );
        if( usuarioExistente == null ){
            throw new UsuarioInexistenteException( "Usuário com uuid " + uuid + " a ser deletado não existe" );
        }

        this.usuarioInterface.deletarUsuario( uuid );
    }
}

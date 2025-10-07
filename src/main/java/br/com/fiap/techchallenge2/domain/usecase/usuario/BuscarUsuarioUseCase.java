package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class BuscarUsuarioUseCase
{

    private final UsuarioInterface usuarioInterface;

    public UsuarioOutput execute( UUID uuid ){

        Usuario usuario = this.usuarioInterface.buscarUsuarioPorUuid( uuid );
        if( usuario == null ){
            throw new UsuarioInexistenteException( "Usuário com o id " + uuid + " não existe" );
        }

        return new UsuarioOutput(
                usuario.getUuid( ),
                usuario.getNome( ),
                usuario.getCpf( ),
                usuario.getEmail( ),
                usuario.getTelefone( ),
                usuario.getEndereco( ),
                usuario.getTipoUsuario( ).getNome( )
        );
    }
}

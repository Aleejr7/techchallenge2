package br.com.fiap.techchallenge2.domain.gateway;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioInterface
{
    void deletarUsuario( UUID id );
    Usuario criarUsuario( Usuario usuario );
    Usuario atualizarUsuario( Usuario usuario );
    Usuario buscarUsuarioPorUuid( UUID id );
    List<Usuario> buscarTodosUsuarios( );
    Usuario buscarUsuarioPorEmail( String email );
    List<Usuario> buscarUsuariosPorTipo(UUID idTipo);
}

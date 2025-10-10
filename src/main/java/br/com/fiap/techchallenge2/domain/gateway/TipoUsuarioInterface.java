package br.com.fiap.techchallenge2.domain.gateway;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;

import java.util.List;
import java.util.UUID;

public interface TipoUsuarioInterface
{
    TipoUsuario buscarTipoUsuarioPorUuid( UUID uuid );

    TipoUsuario buscarTipoUsuarioPorNome( String nome );

    List<TipoUsuario> buscarTodosTiposUsuario();

    TipoUsuario criarTipoUsuario( TipoUsuario tipoUsuario );

    void deletarTipoUsuarioPorNome( String nome );

    TipoUsuario atualizarTipoUsuario(TipoUsuario tipoUsuario);
}

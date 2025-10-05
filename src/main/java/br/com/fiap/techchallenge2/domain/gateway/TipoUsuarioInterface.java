package br.com.fiap.techchallenge2.domain.gateway;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;

import java.util.List;

public interface TipoUsuarioInterface
{
    TipoUsuario buscarTipoUsuarioPorNome( String nome );

    List<TipoUsuario> buscarTodosTiposUsuario();

    TipoUsuario criarTipoUsuario( TipoUsuario tipoUsuario );

    void deletarTipoUsuarioPorNome( String nome );

    TipoUsuario atualizarTipoUsuario(TipoUsuario tipoUsuario);
}

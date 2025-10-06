package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.output.TipoUsuarioOutput;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarTodosTipoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;

    public List<TipoUsuarioOutput> execute( String tipoUsuarioLogado ){

        if ( !tipoUsuarioLogado.equals( "Admin" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'Admin' podem buscar todos os tipos de usuário" );
        }

        List<TipoUsuario> tiposDeUsuarios = this.tipoUsuarioInterface.buscarTodosTiposUsuario(  );

        return tiposDeUsuarios.stream()
                .map(tipoUsuario -> new TipoUsuarioOutput(tipoUsuario.getId(), tipoUsuario.getNome()))
                .toList();
    }
}

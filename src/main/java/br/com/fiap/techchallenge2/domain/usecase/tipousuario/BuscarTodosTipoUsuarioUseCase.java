package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.output.TipoUsuarioOutput;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarTodosTipoUsuarioUseCase
{

    private final TipoUsuarioInterface tipoUsuarioInterface;

    public List<TipoUsuarioOutput> execute(  ){

        List<TipoUsuario> tiposDeUsuarios = this.tipoUsuarioInterface.buscarTodosTiposUsuario(  );

        return tiposDeUsuarios.stream()
                .map(tipoUsuario -> new TipoUsuarioOutput(tipoUsuario.getId(), tipoUsuario.getNome()))
                .toList();
    }
}

package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.output.UsuarioOutput;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarTodosUsuariosUseCase
{
    private final UsuarioInterface usuarioInterface;

    public List<UsuarioOutput> execute(  ){

        return this.usuarioInterface.buscarTodosUsuarios()
                .stream()
                .map(usuario -> new UsuarioOutput(
                        usuario.getUuid(),
                        usuario.getNome(),
                        usuario.getCpf( ),
                        usuario.getEmail(),
                        usuario.getTelefone( ),
                        usuario.getEndereco( ),
                        usuario.getTipoUsuario().getNome()
                ))
                .toList();
    }
}

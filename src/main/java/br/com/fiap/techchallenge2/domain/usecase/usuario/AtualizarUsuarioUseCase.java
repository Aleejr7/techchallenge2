package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.AtualizarUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.UsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarUsuarioUseCase
{

    private final UsuarioInterface usuarioInterface;

    public UsuarioOutput execute( AtualizarUsuarioInput atualizarUsuarioInput ) {

        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorUuid( atualizarUsuarioInput.uuid( ) );
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuário com UUID " + atualizarUsuarioInput.uuid() + " não existe.");
        }

        usuarioExistente.setTelefone( atualizarUsuarioInput.telefone( ) );
        usuarioExistente.setEndereco( atualizarUsuarioInput.endereco( ) );
        Usuario usuarioAtualizado = this.usuarioInterface.atualizarUsuario( usuarioExistente );

        return new UsuarioOutput(
                usuarioAtualizado.getUuid(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getCpf( ),
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getTelefone( ),
                usuarioAtualizado.getEndereco( ),
                usuarioAtualizado.getTipoUsuario().getNome()
        );
    }

}

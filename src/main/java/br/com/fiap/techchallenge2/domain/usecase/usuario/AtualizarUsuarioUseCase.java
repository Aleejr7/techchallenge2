package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.Usuario;

import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.AtualizarUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarUsuarioUseCase
{

    private final UsuarioInterface usuarioInterface;

    public UsuarioOutput execute( AtualizarUsuarioInput usuarioInput ) {

        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorUuid( usuarioInput.uuid( ) );
        if (usuarioExistente == null) {
            throw new UsuarioInexistenteException("Usuário com UUID " + usuarioInput.uuid() + " não existe.");
        }

        usuarioExistente.setNome( usuarioInput.nome( ) );
        usuarioExistente.setTelefone( usuarioInput.telefone( ) );
        usuarioExistente.setEndereco( usuarioInput.endereco( ) );
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

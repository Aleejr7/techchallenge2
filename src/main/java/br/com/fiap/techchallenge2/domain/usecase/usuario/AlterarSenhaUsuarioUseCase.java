package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.usuario.SenhaErradaException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.AlterarSenhaUsuarioInput;
import br.com.fiap.techchallenge2.domain.utils.EncriptadorSenha;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AlterarSenhaUsuarioUseCase
{

    private final UsuarioInterface usuarioInterface;

    public void execute( AlterarSenhaUsuarioInput senhaUsuarioInput ) {

        Usuario usuario = this.usuarioInterface.buscarUsuarioPorEmail( senhaUsuarioInput.email( ) );
        if( usuario == null ) {
            throw new UsuarioInexistenteException( "O usuário com email " + senhaUsuarioInput.email( ) + " não existe" );
        }

        if( !EncriptadorSenha.verificarSenha( senhaUsuarioInput.senhaAntiga( ), usuario.getSenha( ) ) ) {
            throw new SenhaErradaException( "A senha está incorreta" );
        }

        if( !Objects.equals( senhaUsuarioInput.senhaNova( ), senhaUsuarioInput.confirmarSenhaNova( ) ) ){
            throw new SenhaErradaException( "A nova senha e a confirmação de nova senha não coincidem" );
        } else {
            String senhaEncriptada = EncriptadorSenha.encriptar( senhaUsuarioInput.senhaNova() );
            usuario.setSenha( senhaEncriptada );
            this.usuarioInterface.atualizarUsuario( usuario );
        }

    }
}

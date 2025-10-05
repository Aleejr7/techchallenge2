package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.CriarUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.UsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarUsuarioUseCase
{

    private final UsuarioInterface usuarioInterface;
    private final TipoUsuarioInterface tipoUsuarioInterface;

    public UsuarioOutput execute( CriarUsuarioInput usuarioInput ) {

        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorEmail( usuarioInput.email( ) );
        if( usuarioExistente != null ){
            throw new UsuarioJaExisteException( "Já existe um usuário cadastrado com o email: " + usuarioInput.email( ) );
        }

        TipoUsuario tipoUsuarioExistente = this.tipoUsuarioInterface.buscarTipoUsuarioPorNome( usuarioInput.tipoUsuario( ).nome( ) );
        if(tipoUsuarioExistente == null){
            throw new TipoUsuarioInexistenteException(
                    "Tipo do usuário " + usuarioInput.tipoUsuario( ).nome( ) + " não existe, verifique a lista de Tipos de Usuário existentes" );
        }

        Usuario usuario = new Usuario(
                usuarioInput.nome( ),
                usuarioInput.cpf( ),
                usuarioInput.email( ),
                usuarioInput.senha( ),
                usuarioInput.telefone( ),
                usuarioInput.endereco( ),
                tipoUsuarioExistente
        );

        Usuario usuarioCriado = this.usuarioInterface.criarUsuario( usuario );

        return new UsuarioOutput(
                usuarioCriado.getUuid(),
                usuarioCriado.getNome(),
                usuarioCriado.getCpf(),
                usuarioCriado.getEmail(),
                usuarioCriado.getTelefone(),
                usuarioCriado.getEndereco(),
                usuarioCriado.getTipoUsuario().getNome()
        );
    }
}

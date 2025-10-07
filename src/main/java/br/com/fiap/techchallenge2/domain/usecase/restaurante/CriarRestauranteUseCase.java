package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteJaExisteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.restaurante.CriarRestauranteInput;
import br.com.fiap.techchallenge2.domain.output.restaurante.CriarRestauranteOutput;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarRestauranteUseCase
{

    private final RestauranteInterface restauranteInterface;
    private final UsuarioInterface usuarioInterface;

    public CriarRestauranteOutput execute ( CriarRestauranteInput restauranteInput ) {

        Restaurante restauranteExistente = this.restauranteInterface.buscarRestaurantePorNome( restauranteInput.nome( ) );
        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorUuid( restauranteInput.uuidDonoRestaurante( ) );

        if ( restauranteExistente != null ) {
            throw new RestauranteJaExisteException( "O restaurante com o nome " + restauranteInput.nome( ) + " já existe." );
        }
        if ( usuarioExistente == null ) {
            throw new UsuarioInexistenteException( "Usuário - DonoRestaurante com o UUID " + restauranteInput.uuidDonoRestaurante() + " não existe." );
        }
        if ( !usuarioExistente.getTipoUsuario().getNome().equals( "DonoRestaurante" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'DonoRestaurante' podem criar um restaurante." );
        }

        Restaurante restaurante = new Restaurante(
                restauranteInput.nome(),
                restauranteInput.endereco(),
                restauranteInput.tipoCozinha(),
                restauranteInput.horarioAbertura(),
                restauranteInput.horarioFechamento(),
                usuarioExistente
        );

        Restaurante restauranteCriado = this.restauranteInterface.criarRestaurante( restaurante );

        return new CriarRestauranteOutput(
                restauranteCriado.getUuid(),
                restauranteCriado.getNome(),
                restauranteCriado.getEndereco(),
                restauranteCriado.getTipoCozinha(),
                restauranteCriado.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteCriado.getHorarioFuncionamento().horarioFechamento().toString(),
                new UsuarioOutput(
                        usuarioExistente.getUuid(),
                        usuarioExistente.getNome(),
                        usuarioExistente.getCpf(),
                        usuarioExistente.getEmail(),
                        usuarioExistente.getTelefone(),
                        usuarioExistente.getEndereco(),
                        usuarioExistente.getTipoUsuario().getNome()
                )
        );
    }
}

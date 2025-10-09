package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteJaExisteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.restaurante.AtualizarRestauranteInput;
import br.com.fiap.techchallenge2.domain.output.restaurante.BuscarRestauranteOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarRestauranteUseCase
{

    private final RestauranteInterface restauranteInterface;
    private final UsuarioInterface usuarioInterface;

    public BuscarRestauranteOutput execute( AtualizarRestauranteInput restauranteInput ) {

        Restaurante restauranteExistente = this.restauranteInterface.buscarRestaurantePorUuid( restauranteInput.uuidRestaurante() );
        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorUuid( restauranteInput.uuidDonoRestaurante() );

        if ( restauranteExistente == null ) {
            throw new RestauranteInexistenteException( "O restaurante com o UUID " + restauranteInput.uuidRestaurante() + " não existe." );
        }
        if ( usuarioExistente == null ) {
            throw new UsuarioInexistenteException( "Usuário - DonoRestaurante com o UUID " + restauranteInput.uuidDonoRestaurante() + " não existe." );
        }
        if (usuarioExistente.getUuid() != restauranteExistente.getDonoRestaurante().getUuid()) {
            throw new RestauranteInexistenteException( "Usuário com o UUID " + usuarioExistente.getUuid()
                                                        + " não é dono do restaurante " + restauranteExistente.getNome() + "." );
        }

        restauranteExistente.setNome( restauranteInput.nome() );
        restauranteExistente.setEndereco( restauranteInput.endereco() );
        restauranteExistente.setTipoCozinha( restauranteInput.tipoCozinha() );
        restauranteExistente.setHorarioFuncionamento( restauranteInput.horarioAbertura(), restauranteInput.horarioFechamento() );
        Restaurante restauranteAtualizado = this.restauranteInterface.atualizarRestaurante( restauranteExistente );

        return new BuscarRestauranteOutput(
                restauranteAtualizado.getUuid(),
                restauranteAtualizado.getNome(),
                restauranteAtualizado.getEndereco(),
                restauranteAtualizado.getTipoCozinha(),
                restauranteAtualizado.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteAtualizado.getHorarioFuncionamento().horarioFechamento().toString()
        );
    }
}

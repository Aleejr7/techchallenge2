package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.output.restaurante.BuscarRestauranteOutput;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class BuscarRestauranteUseCase
{

    private final RestauranteInterface restauranteInterface;

    public BuscarRestauranteOutput execute( UUID uuidRestaurante ) {

        Restaurante restauranteExistente = this.restauranteInterface.buscarRestaurantePorUuid( uuidRestaurante );
        if ( restauranteExistente == null ) {
            throw new RestauranteInexistenteException( "O restaurante com o UUID: " + uuidRestaurante + " não existe." );
        }


        return new BuscarRestauranteOutput(
                restauranteExistente.getUuid(),
                restauranteExistente.getNome(),
                restauranteExistente.getEndereco(),
                restauranteExistente.getTipoCozinha(),
                restauranteExistente.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteExistente.getHorarioFuncionamento().horarioFechamento().toString()
        );
    }
}

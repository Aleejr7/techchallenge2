package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.output.restaurante.BuscarRestauranteOutput;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BuscarTodosRestaurantesUseCase
{

    private final RestauranteInterface restauranteInterface;

    public List<BuscarRestauranteOutput> execute() {

        List<Restaurante> restaurantes = this.restauranteInterface.buscarTodosRestaurantes( );
        if ( restaurantes == null ) {
            throw new RestauranteInexistenteException( "Não existe nenhum restaurante no sistema." );
        }

        return restaurantes.stream()
                .map(restaurante -> new BuscarRestauranteOutput(
                        restaurante.getUuid(),
                        restaurante.getNome(),
                        restaurante.getEndereco(),
                        restaurante.getTipoCozinha(),
                        restaurante.getHorarioFuncionamento().horarioAbertura().toString(),
                        restaurante.getHorarioFuncionamento().horarioFechamento().toString()
                ))
                .collect(Collectors.toList());
    }
}

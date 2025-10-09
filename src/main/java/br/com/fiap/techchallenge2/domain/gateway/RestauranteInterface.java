package br.com.fiap.techchallenge2.domain.gateway;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;

import java.util.List;
import java.util.UUID;

public interface RestauranteInterface
{
    Restaurante criarRestaurante( Restaurante restaurante );

    Restaurante buscarRestaurantePorUuid( UUID uuid );

    Restaurante buscarRestaurantePorNome( String nome );

    List<Restaurante> buscarTodosRestaurantes( );

    void deletarRestaurantePorUuid( UUID uuid );

    Restaurante atualizarRestaurante( Restaurante restaurante );
}

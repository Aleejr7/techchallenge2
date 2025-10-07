package br.com.fiap.techchallenge2.domain.gateway;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;

import java.util.UUID;

public interface RestauranteInterface
{
    Restaurante criarRestaurante( Restaurante restaurante );

    Restaurante buscarRestaurantePorUuid( UUID uuid );

    Restaurante buscarRestaurantePorNome( String nome );

    void deletarRestaurantePorUuid( UUID uuid );
}

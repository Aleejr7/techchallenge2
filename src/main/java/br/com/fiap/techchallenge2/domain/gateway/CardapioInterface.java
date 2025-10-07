package br.com.fiap.techchallenge2.domain.gateway;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;

import java.util.UUID;

public interface CardapioInterface
{

    Cardapio buscarCardapioPorUuid( UUID uuid );

    Cardapio adicionarItemAoCardapio( UUID uuidCardapio, UUID uuidItem );

    void removerItemDoCardapio( UUID uuidCardapio, UUID uuidItem );

    Cardapio atualizarCardapio( Cardapio cardapio );

    Cardapio buscarCardapioPorUUidRestaurante( UUID uuidRestaurante );

    void deletarCardapioPorUuid( UUID uuid );
}

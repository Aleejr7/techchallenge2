package br.com.fiap.techchallenge2.domain.gateway;

import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;

import java.util.UUID;

public interface ItemCardapioInterface
{
    ItemCardapio criarItemCardapio( ItemCardapio itemCardapio );

    ItemCardapio buscarItemCardapioPorUuid( UUID uuid );

    ItemCardapio atualizarItemCardapio( ItemCardapio itemCardapio );

    void deletarItemCardapioPorUuid( UUID uuid );

    void deletarTodosItensPorUuidCardapio( UUID uuidCardapio);
}

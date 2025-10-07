package br.com.fiap.techchallenge2.domain.output.cardapio;

import br.com.fiap.techchallenge2.domain.output.itemcardapio.ItemCardapioOutput;

import java.util.List;
import java.util.UUID;

public record CardapioOutput(
        UUID uuid,
        String nome,
        List<ItemCardapioOutput> itensCardapio
)
{
}

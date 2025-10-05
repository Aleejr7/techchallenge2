package br.com.fiap.techchallenge2.domain.input.itemcardapio;

import java.util.UUID;

public record CriarItemCardapioInput(
        String nome,
        String descricao,
        double preco,
        String imagemUrl,
        UUID cardapioUuid
)
{
}

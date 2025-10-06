package br.com.fiap.techchallenge2.domain.input.cardapio;

import java.util.UUID;

public record AlterarNomeCardapioInput(
        UUID uuid,
        String nome
)
{
}

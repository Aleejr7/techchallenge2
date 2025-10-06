package br.com.fiap.techchallenge2.domain.output;

import java.util.List;
import java.util.UUID;

public record CardapioOutput(
        UUID uuid,
        String nome,
        List<ItemCardapioOutput> itensCardapio
)
{
}

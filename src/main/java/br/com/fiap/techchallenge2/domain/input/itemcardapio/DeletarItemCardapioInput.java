package br.com.fiap.techchallenge2.domain.input.itemcardapio;

import java.util.UUID;

public record DeletarItemCardapioInput(
        UUID uuidItemCardapio,
        UUID uuidCardapio,
        String tipoUsuarioLogado
)
{
}

package br.com.fiap.techchallenge2.domain.input.itemcardapio;

import java.util.UUID;

public record AtualizarItemCardapioInput(
        UUID uuid,
        String nome,
        String descricao,
        double preco,
        String imagemUrl,
        String tipoUsuarioLogado
)
{
}

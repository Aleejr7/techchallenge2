package br.com.fiap.techchallenge2.domain.output.itemcardapio;

import java.util.UUID;

public record AtualizarItemCardapioOutput(
        UUID uuid,
        String nome,
        String descricao,
        Double preco,
        DisponibilidadePedidoOutput disponibilidadePedido,
        String imagemUrl,
        UUID cardapioId
)
{
}

package br.com.fiap.techchallenge2.domain.output;

import java.util.UUID;

public record ItemCardapioOutput(
        UUID uuid,
        String nome,
        String descricao,
        Double preco,
        DisponibilidadePedidoOutput disponibilidadePedido,
        String imagemUrl,
        UUID uuidCardapio
)
{
}

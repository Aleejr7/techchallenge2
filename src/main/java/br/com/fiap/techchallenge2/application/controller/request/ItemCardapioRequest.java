package br.com.fiap.techchallenge2.application.controller.request;

import java.util.UUID;

public record ItemCardapioRequest(
        UUID uuid,
        String nome,
        String descricao,
        Double preco,
        String imageUrl,
        String disponibilidadePedido,
        String imagemUrl,
        UUID cardapioId
) {
}

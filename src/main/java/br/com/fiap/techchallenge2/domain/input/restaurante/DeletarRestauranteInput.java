package br.com.fiap.techchallenge2.domain.input.restaurante;

import java.util.UUID;

public record DeletarRestauranteInput(
        UUID uuidRestaurante,
        UUID uuidDonoRestaurante
)
{
}

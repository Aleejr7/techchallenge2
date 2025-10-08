package br.com.fiap.techchallenge2.domain.input.restaurante;

import java.util.UUID;

public record AtualizarRestauranteInput(
        UUID uuidRestaurante,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioAbertura,
        String horarioFechamento,
        UUID uuidDonoRestaurante
)
{
}

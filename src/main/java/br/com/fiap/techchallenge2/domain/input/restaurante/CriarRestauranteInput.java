package br.com.fiap.techchallenge2.domain.input.restaurante;

import java.util.UUID;

public record CriarRestauranteInput (
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioAbertura,
        String horarioFechamento,
        UUID uuidDonoRestaurante
)
{
}

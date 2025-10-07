package br.com.fiap.techchallenge2.domain.output.restaurante;

import java.util.UUID;

public record BuscarRestauranteOutput(
        UUID uuid,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioAbertura,
        String horarioFechamento
)
{
}

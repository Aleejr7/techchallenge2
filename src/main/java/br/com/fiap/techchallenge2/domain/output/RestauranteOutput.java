package br.com.fiap.techchallenge2.domain.output;

import java.util.UUID;

public record RestauranteOutput (
        UUID uuid,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioAbertura,
        String horarioFechamento,
        UsuarioOutput donoRestaurante
)
{
}

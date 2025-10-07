package br.com.fiap.techchallenge2.domain.output.restaurante;

import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;

import java.util.UUID;

public record CriarRestauranteOutput(
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

package br.com.fiap.techchallenge2.domain.input.usuario;

import java.util.UUID;

public record AtualizarUsuarioInput(
        UUID uuid,
        String nome,
        String telefone,
        String endereco
)
{
}

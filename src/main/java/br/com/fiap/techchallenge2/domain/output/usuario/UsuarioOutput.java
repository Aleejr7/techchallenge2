package br.com.fiap.techchallenge2.domain.output.usuario;

import java.util.UUID;

public record UsuarioOutput(
        UUID uuid,
        String nome,
        String cpf,
        String email,
        String telefone,
        String endereco,
        String tipoUsuario
)
{
}

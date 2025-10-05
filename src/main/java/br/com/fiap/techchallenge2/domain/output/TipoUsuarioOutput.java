package br.com.fiap.techchallenge2.domain.output;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;

import java.util.UUID;

public record TipoUsuarioOutput(
        UUID uuid,
        String nome
) {
}

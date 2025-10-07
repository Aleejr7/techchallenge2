package br.com.fiap.techchallenge2.domain.output.tipousuario;

import java.util.UUID;

public record TipoUsuarioOutput(
        UUID uuid,
        String nome
) {
}

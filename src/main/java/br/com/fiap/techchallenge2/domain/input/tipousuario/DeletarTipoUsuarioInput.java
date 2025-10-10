package br.com.fiap.techchallenge2.domain.input.tipousuario;

import java.util.UUID;

public record DeletarTipoUsuarioInput(
        UUID uuid,
        String tipoUsuarioLogado
)
{
}

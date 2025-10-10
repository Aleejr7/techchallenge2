package br.com.fiap.techchallenge2.domain.input.tipousuario;

import java.util.UUID;

public record AtualizarTipoUsuarioInput(
        UUID uuid,
        String nome,
        String tipoUsuarioLogado
)
{
}

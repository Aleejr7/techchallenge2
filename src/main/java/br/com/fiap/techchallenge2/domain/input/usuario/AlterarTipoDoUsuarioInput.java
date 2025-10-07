package br.com.fiap.techchallenge2.domain.input.usuario;

import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;

import java.util.UUID;

public record AlterarTipoDoUsuarioInput(
        UUID uuidUsuario,
        TipoUsuarioInput tipoUsuarioInput,
        String tipoUsuarioLogado
)
{
}

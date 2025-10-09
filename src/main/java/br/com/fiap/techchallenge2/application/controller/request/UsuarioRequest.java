package br.com.fiap.techchallenge2.application.controller.request;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;

public record CriarUsuarioRequest(
        String nome,
        String cpf,
        String email,
        String senha,
        String telefone,
        String endereco,
        TipoUsuarioInput tipoUsuario
) {
}

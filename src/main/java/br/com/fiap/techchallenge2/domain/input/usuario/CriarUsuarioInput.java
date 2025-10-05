package br.com.fiap.techchallenge2.domain.input.usuario;

import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;

public record CriarUsuarioInput(
    String nome,
    String cpf,
    String email,
    String senha,
    String telefone,
    String endereco,
    TipoUsuarioInput tipoUsuario
)
{
}

package br.com.fiap.techchallenge2.domain.input.usuario;

public record AlterarSenhaUsuarioInput(
        String email,
        String senhaAntiga,
        String senhaNova,
        String confirmarSenhaNova
)
{
}

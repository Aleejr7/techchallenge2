package br.com.fiap.techchallenge2.infra.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsuarioModelTest {
    @Test
    public void deveCriarUsuarioComConstrutorCompleto(){
        var uuid = UUID.randomUUID();
        var nome = "nome usuario";
        var cpf = "234235";
        var email = "em@gmail.com";
        var senha = "123456";
        var telefone = "7182403";
        var endereco = "rua do usuario";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel();

        UsuarioModel usuarioModel = new UsuarioModel(uuid,
                nome,
                cpf,
                email,
                senha,
                telefone,
                endereco,
                tipoUsuarioModel
        );

        assertThat(usuarioModel).satisfies(u -> {
            assertThat(u.getUuid()).isEqualTo(uuid);
            assertThat(u.getNome()).isEqualTo(nome);
            assertThat(u.getCpf()).isEqualTo(cpf);
            assertThat(u.getEmail()).isEqualTo(email);
            assertThat(u.getSenha()).isEqualTo(senha);
            assertThat(u.getTelefone()).isEqualTo(telefone);
            assertThat(u.getEndereco()).isEqualTo(endereco);
            assertThat(u.getTipoUsuarioModel()).isEqualTo(tipoUsuarioModel);
        });
    }
    @Test
    public void deveCriarUsuarioConstrutorSemUUID(){
        var nome = "nome usuario";
        var cpf = "234235";
        var email = "em@gmail.com";
        var senha = "123456";
        var telefone = "7182403";
        var endereco = "rua do usuario";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel();

        UsuarioModel usuarioModel = new UsuarioModel(
                nome,
                cpf,
                email,
                senha,
                telefone,
                endereco,
                tipoUsuarioModel
        );

        assertThat(usuarioModel).satisfies(u -> {
            assertThat(u.getNome()).isEqualTo(nome);
            assertThat(u.getCpf()).isEqualTo(cpf);
            assertThat(u.getEmail()).isEqualTo(email);
            assertThat(u.getSenha()).isEqualTo(senha);
            assertThat(u.getTelefone()).isEqualTo(telefone);
            assertThat(u.getEndereco()).isEqualTo(endereco);
            assertThat(u.getTipoUsuarioModel()).isEqualTo(tipoUsuarioModel);
        });
    }
    @Test
    public void deveCriarUsuarioComConstrutorPadrao(){
        var usuarioModel = new UsuarioModel();

        assertThat(usuarioModel).isInstanceOf(usuarioModel.getClass());
    }
}

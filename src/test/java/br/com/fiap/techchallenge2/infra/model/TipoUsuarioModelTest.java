package br.com.fiap.techchallenge2.infra.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TipoUsuarioModelTest {

    @Test
    public void deveCriarModeloTipoUsuario(){
        var uuid = UUID.randomUUID();
        var nome = "admin";

        TipoUsuarioModel model = new TipoUsuarioModel(uuid,nome);

        assertThat(model).satisfies(m -> {
            assertThat(m.getId()).isEqualTo(uuid);
            assertThat(m.getNome()).isEqualTo(nome);
        });
    }
    @Test
    public void deveAlterarModeloTipoUsuario(){
        var uuid = UUID.randomUUID();
        var novoNome = "Administrador";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,"Especialista");
        tipoUsuarioModel.setNome(novoNome);

        assertThat(tipoUsuarioModel).satisfies(m ->{
            assertThat(m.getNome()).isEqualTo(novoNome);

        });
    }
    @Test
    public void deveCriarTipoUsuarioComConstrutorPadraoESetarNome(){
        var nome = "Administrador";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel();
        tipoUsuarioModel.setNome(nome);

        assertThat(tipoUsuarioModel).satisfies(m -> {
            assertThat(m.getNome()).isEqualTo(nome);
        });
    }
    @Test
    public void deveCriarTipoUsuarioComConstrutorPadrao(){
        var tipoUsuarioModel = new TipoUsuarioModel();

        assertThat(tipoUsuarioModel).isInstanceOf(TipoUsuarioModel.class);
    }



}

package br.com.fiap.techchallenge2.infra.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RestauranteModelTeste {

    @Test
    public void deveCriarRestauranteComConstrutorCompleto(){
        // var uuid = UUID.randomUUID();
        var nomeRestaurante = "Casa dos sabores";
        var endereco = "Rua dos sabores";
        var tpCozinha = "Japonesa";
        var hora = new HorarioFuncionamentoModel(converterStringParaLocalTime("10:00")
                ,converterStringParaLocalTime("22:00"));
        UsuarioModel dono = new UsuarioModel();

        RestauranteModel restauranteModel = new RestauranteModel(
                nomeRestaurante,endereco,tpCozinha,hora,dono
        );

        assertThat(restauranteModel).satisfies(r -> {
            assertThat(r.getNome()).isEqualTo(nomeRestaurante);
            assertThat(r.getEndereco()).isEqualTo(endereco);
            assertThat(r.getTipoCozinha()).isEqualTo(tpCozinha);
            assertThat(r.getHorarioFuncionamento()).isEqualTo(hora);
            assertThat(r.getDonoRestaurante()).isEqualTo(dono);
        });
    }
    @Test
    public void deveCriarRestauranteComConstrutorSemUUID(){
        var nomeRestaurante = "Casa dos sabores";
        var endereco = "Rua dos sabores";
        var tpCozinha = "Japonesa";
        var hora = new HorarioFuncionamentoModel(converterStringParaLocalTime("10:00")
                ,converterStringParaLocalTime("22:00"));
        UsuarioModel dono = new UsuarioModel();

        RestauranteModel restauranteModel = new RestauranteModel(
                nomeRestaurante,endereco,tpCozinha,hora,dono
        );

        assertThat(restauranteModel).satisfies(r -> {
            assertThat(r.getNome()).isEqualTo(nomeRestaurante);
            assertThat(r.getEndereco()).isEqualTo(endereco);
            assertThat(r.getTipoCozinha()).isEqualTo(tpCozinha);
            assertThat(r.getHorarioFuncionamento()).isEqualTo(hora);
            assertThat(r.getDonoRestaurante()).isEqualTo(dono);
        });
    }
    @Test
    public void deveCriarRestauranteComConstrutorPadrao(){
        var restauranteModel = new RestauranteModel();

        assertThat(restauranteModel).isInstanceOf(RestauranteModel.class);
    }
    private LocalTime converterStringParaLocalTime(String horario ) {
        String[] partes = horario.split(":");
        int hora = Integer.parseInt(partes[0]);
        int minuto = Integer.parseInt(partes[1]);
        return LocalTime.of(hora, minuto);
    }
}

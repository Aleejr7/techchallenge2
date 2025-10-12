package br.com.fiap.techchallenge2.infra.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HorarioFuncionamentoModelTest {
    @Test
    void deveCriarHorarioEVerificarGetters() {
        LocalTime abertura = LocalTime.of(9, 0);
        LocalTime fechamento = LocalTime.of(18, 0);

        HorarioFuncionamentoModel horario = new HorarioFuncionamentoModel(abertura, fechamento);

        assertThat(horario.horarioAbertura()).isEqualTo(abertura);
        assertThat(horario.horarioFechamento()).isEqualTo(fechamento);
    }

    @Test
    void deveVerificarIgualdadeEHashCode() {
        LocalTime abertura1 = LocalTime.of(10, 0);
        LocalTime fechamento1 = LocalTime.of(22, 0);

        HorarioFuncionamentoModel horario1 = new HorarioFuncionamentoModel(abertura1, fechamento1);
        HorarioFuncionamentoModel horario2 = new HorarioFuncionamentoModel(abertura1, fechamento1);

        HorarioFuncionamentoModel horarioDiferente = new HorarioFuncionamentoModel(LocalTime.of(8, 0), LocalTime.of(17, 0));

        assertThat(horario1).isEqualTo(horario2);
        assertThat(horario1).hasSameHashCodeAs(horario2);
        assertThat(horario1).isNotEqualTo(horarioDiferente);
        assertThat(horario1).isNotEqualTo(null);
    }

    @Test
    void deveVerificarRepresentacaoEmString() {
        LocalTime abertura = LocalTime.of(8, 30);
        LocalTime fechamento = LocalTime.of(19, 0);
        HorarioFuncionamentoModel horario = new HorarioFuncionamentoModel(abertura, fechamento);

        assertThat(horario.toString()).contains("horarioAbertura=08:30", "horarioFechamento=19:00");
    }
}

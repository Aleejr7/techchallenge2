package br.com.fiap.techchallenge2.infra.model;

import java.time.LocalTime;

public record HorarioFuncionamentoModel(
        LocalTime horarioAbertura,
        LocalTime horarioFechamento
) {
}

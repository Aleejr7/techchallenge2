package br.com.fiap.techchallenge2.domain.entity;

import java.time.LocalTime;

public record HorarioFuncionamento(
        LocalTime horarioAbertura,
        LocalTime horarioFechamento
)
{
}

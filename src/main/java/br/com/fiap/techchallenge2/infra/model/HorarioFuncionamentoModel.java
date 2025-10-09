package br.com.fiap.techchallenge2.infra.model;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public record HorarioFuncionamentoModel(LocalTime horarioAbertura, LocalTime horarioFechamento) {}

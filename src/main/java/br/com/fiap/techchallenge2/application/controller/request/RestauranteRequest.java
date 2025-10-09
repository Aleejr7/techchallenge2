package br.com.fiap.techchallenge2.application.controller.request;

import br.com.fiap.techchallenge2.domain.entity.HorarioFuncionamento;
import br.com.fiap.techchallenge2.domain.entity.Usuario;

import java.util.UUID;

public record RestauranteRequest (
    UUID uuid,
    String nome,
    String endereco,
    String tipoCozinha,
    String horarioAbertura,
    String horarioFechamento,
    UUID donoRestaurante

) {}

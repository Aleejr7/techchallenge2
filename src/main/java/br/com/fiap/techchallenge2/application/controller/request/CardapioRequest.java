package br.com.fiap.techchallenge2.application.controller.request;

import java.util.List;
import java.util.UUID;

public record CardapioRequest(
        UUID uuid,
        String nome,
        UUID restauranteId
) {


}

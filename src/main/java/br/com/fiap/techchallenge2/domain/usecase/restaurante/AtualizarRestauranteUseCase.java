package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.restaurante.AtualizarRestauranteInput;
import br.com.fiap.techchallenge2.domain.output.restaurante.BuscarRestauranteOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarRestauranteUseCase
{

    private final RestauranteInterface restauranteInterface;
    private final UsuarioInterface usuarioInterface;

    public BuscarRestauranteOutput execute( AtualizarRestauranteInput restauranteInput ) {

        return null;
    }

}

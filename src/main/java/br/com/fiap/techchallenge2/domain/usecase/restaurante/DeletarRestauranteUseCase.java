package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.restaurante.DeletarRestauranteInput;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class DeletarRestauranteUseCase
{

    private final RestauranteInterface restauranteInterface;
    private final UsuarioInterface usuarioInterface;
    private final CardapioInterface cardapioInterface;
    private final ItemCardapioInterface itemCardapioInterface;

    public void execute( DeletarRestauranteInput restauranteInput ) {

        Usuario usuarioExistente = this.usuarioInterface.buscarUsuarioPorUuid( restauranteInput.uuidDonoRestaurante( ) );
        Restaurante restauranteExistente = this.restauranteInterface.buscarRestaurantePorUuid( restauranteInput.uuidRestaurante( ) );
        if ( usuarioExistente == null ) {
            throw new UsuarioInexistenteException( "O usuário, DonoRestaurante com o UUID: " + restauranteInput.uuidDonoRestaurante( ) + " não existe." );
        }
        if ( restauranteExistente == null ) {
            throw new RestauranteInexistenteException( "O restaurante com o UUID: " + restauranteInput.uuidRestaurante( ) + " não existe." );
        }
        if ( restauranteExistente.getDonoRestaurante( ).getUuid( ) != restauranteInput.uuidDonoRestaurante( ) ) {
            throw new RestauranteInexistenteException( "O usuário, DonoRestaurante com o UUID: " + restauranteInput.uuidDonoRestaurante( ) +
                                                                    " não é dono do restaurante " + restauranteExistente.getNome( ) + "." );
        }

        Cardapio cardapioExistente = this.cardapioInterface.buscarCardapioPorUUidRestaurante( restauranteExistente.getUuid( ) );
        if ( cardapioExistente != null ) {
            this.itemCardapioInterface.deletarTodosItensPorUuidCardapio( cardapioExistente.getUuid( ) );
            this.cardapioInterface.deletarCardapioPorUuid( cardapioExistente.getUuid( ) );
        }

        this.restauranteInterface.deletarRestaurantePorUuid( restauranteExistente.getUuid() );
    }
}

package br.com.fiap.techchallenge2.domain.usecase.cardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.output.CardapioOutput;
import br.com.fiap.techchallenge2.domain.output.DisponibilidadePedidoOutput;
import br.com.fiap.techchallenge2.domain.output.ItemCardapioOutput;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class BuscarCardapioUseCase
{

    private final CardapioInterface cardapioInterface;

    public CardapioOutput execute ( UUID uuid ) {

        Cardapio cardapioExistente = this.cardapioInterface.buscarCardapioPorUuid( uuid );
        if ( cardapioExistente == null ) {
            throw new CardapioInexistenteException( "O cardápio com o UUID " + uuid + " não existe" );
        }

        final Cardapio cardapioAtualizado = cardapioExistente;

        return new CardapioOutput(
                cardapioAtualizado.getUuid(),
                cardapioAtualizado.getNome(),
                cardapioAtualizado.getItensCardapio().stream()
                        .map(item -> new ItemCardapioOutput(
                                item.getUuid(),
                                item.getNome(),
                                item.getDescricao(),
                                item.getPreco(),
                                new DisponibilidadePedidoOutput(item.getDisponibilidadePedido().getDescricao()),
                                item.getImagemUrl(),
                                cardapioAtualizado.getUuid()
                        )).toList()
        );
    }
}

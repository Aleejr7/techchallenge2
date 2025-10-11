package br.com.fiap.techchallenge2.domain.usecase.cardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.input.cardapio.AlterarNomeCardapioInput;
import br.com.fiap.techchallenge2.domain.output.cardapio.CardapioOutput;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.ItemCardapioOutput;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.DisponibilidadePedidoOutput;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AlterarNomeCardapioUseCase
{

    private final CardapioInterface cardapioInterface;

    public CardapioOutput execute ( AlterarNomeCardapioInput cardapioInput ) {

        if ( !cardapioInput.tipoUsuarioLogado().equals( "DonoRestaurante" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo DonoRestaurante podem alterar o nome do cardápio" );
        }

        Cardapio cardapioExistente = this.cardapioInterface.buscarCardapioPorUuid( cardapioInput.uuid( ) );
        if ( cardapioExistente == null ) {
            throw new CardapioInexistenteException( "O cardápio com o UUID " + cardapioInput.uuid( ) + " não existe" );
        }

        cardapioExistente.setNome( cardapioInput.nome( ) );
        final Cardapio cardapioAtualizado = this.cardapioInterface.atualizarCardapio( cardapioExistente );

        List<ItemCardapioOutput> itensCardapio = new ArrayList<>();
        if (cardapioAtualizado.getItensCardapio() != null){
            itensCardapio = cardapioAtualizado.getItensCardapio().stream()
                    .map(item -> new ItemCardapioOutput(
                            item.getUuid(),
                            item.getNome(),
                            item.getDescricao(),
                            item.getPreco(),
                            new DisponibilidadePedidoOutput(item.getDisponibilidadePedido().getDescricao()),
                            item.getImagemUrl(),
                            cardapioAtualizado.getUuid()
                    )).toList();
        }

        return new CardapioOutput(
                cardapioAtualizado.getUuid(),
                cardapioAtualizado.getNome(),
                itensCardapio
        );
    }
}

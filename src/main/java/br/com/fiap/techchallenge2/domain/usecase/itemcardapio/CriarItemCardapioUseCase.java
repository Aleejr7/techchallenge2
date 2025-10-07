package br.com.fiap.techchallenge2.domain.usecase.itemcardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.CriarItemCardapioInput;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.DisponibilidadePedidoOutput;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.ItemCardapioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarItemCardapioUseCase
{

    private final ItemCardapioInterface itemCardapioInterface;
    private final CardapioInterface cardapioInterface;

    public ItemCardapioOutput execute ( CriarItemCardapioInput itemCardapioInput ){

        if ( !itemCardapioInput.tipoUsuarioLogado().equals( "DonoRestaurante" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'DonoRestaurante' podem criar itens do cardápio" );
        }

        Cardapio cardapio = this.cardapioInterface.buscarCardapioPorUuid( itemCardapioInput.cardapioUuid( ) );
        if( cardapio == null ){
            throw new CardapioInexistenteException( "Cardápio com uuid " + itemCardapioInput.cardapioUuid( ) + " a ser criado o item não existe" );
        }
        if (cardapio.getItensCardapio( ).stream( )
                .anyMatch( item -> item.getNome( ).equals( itemCardapioInput.nome( ) ))) {
            throw new ItemCardapioJaExisteException( "Item com o nome " + itemCardapioInput.nome( ) + " já existe no cardápio " + cardapio.getNome( ) );
        }

        ItemCardapio itemCardapio = new ItemCardapio(
                itemCardapioInput.nome( ),
                itemCardapioInput.descricao( ),
                itemCardapioInput.preco( ),
                DisponibilidadePedido.RESTAURANTE, itemCardapioInput.imagemUrl( ),
                cardapio.getUuid()
        );

        itemCardapio = this.itemCardapioInterface.criarItemCardapio( itemCardapio );
        this.cardapioInterface.adicionarItemAoCardapio( cardapio.getUuid( ), itemCardapio.getUuid( ) );

        return new ItemCardapioOutput(
                itemCardapio.getUuid( ),
                itemCardapio.getNome( ),
                itemCardapio.getDescricao( ),
                itemCardapio.getPreco( ),
                new DisponibilidadePedidoOutput(
                        itemCardapio.getDisponibilidadePedido( ).getDescricao() ),
                itemCardapio.getImagemUrl( ),
                cardapio.getUuid()
        );
    }
}

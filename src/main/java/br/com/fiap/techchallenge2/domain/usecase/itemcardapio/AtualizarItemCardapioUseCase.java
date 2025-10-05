package br.com.fiap.techchallenge2.domain.usecase.itemcardapio;

import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.AtualizarItemCardapioInput;
import br.com.fiap.techchallenge2.domain.output.AtualizarItemCardapioOutput;
import br.com.fiap.techchallenge2.domain.output.DisponibilidadePedidoOutput;
import br.com.fiap.techchallenge2.domain.output.ItemCardapioOutput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarItemCardapioUseCase
{

    private final ItemCardapioInterface itemCardapioInterface;

    public AtualizarItemCardapioOutput execute ( AtualizarItemCardapioInput itemCardapioInput ){

        ItemCardapio itemCardapio = this.itemCardapioInterface.buscarItemCardapioPorUuid( itemCardapioInput.uuid() );
        if ( itemCardapio == null ) {
            throw new ItemCardapioInexistenteException( "O item do cardápio com UUID " + itemCardapioInput.uuid() + " não existe" );
        }

        itemCardapio.setNome( itemCardapioInput.nome( ) );
        itemCardapio.setDescricao( itemCardapioInput.descricao( ) );
        itemCardapio.setPreco( itemCardapioInput.preco( ) );
        itemCardapio.setImagemUrl( itemCardapioInput.imagemUrl( ) );
        itemCardapio = this.itemCardapioInterface.atualizarItemCardapio( itemCardapio );

        return new AtualizarItemCardapioOutput(
                itemCardapio.getUuid( ),
                itemCardapio.getNome( ),
                itemCardapio.getDescricao( ),
                itemCardapio.getPreco( ),
                new DisponibilidadePedidoOutput( itemCardapio.getDisponibilidadePedido( ).getDescricao( ) ),
                itemCardapio.getImagemUrl(  )
        );
    }
}

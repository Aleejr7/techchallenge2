package br.com.fiap.techchallenge2.domain.usecase.itemcardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.DeletarItemCardapioInput;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DeletarItemCardapioUseCase
{

    private final CardapioInterface cardapioInterface;

    public void execute ( DeletarItemCardapioInput itemCardapioInput ) {

        if ( !itemCardapioInput.tipoUsuarioLogado().equals( "DonoRestaurante" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'DonoRestaurante' podem deletar itens do cardápio" );
        }

        Cardapio cardapio = this.cardapioInterface.buscarCardapioPorUuid( itemCardapioInput.uuidCardapio() );
        if ( cardapio == null ) {
            throw new CardapioInexistenteException( "O cardápio com o uuid " + itemCardapioInput.uuidCardapio() + " a ser removido o item não existe" );
        }

        boolean itemExisteNoCardapio = cardapio.getItensCardapio().stream()
                .anyMatch(item -> item.getUuid( ).equals( itemCardapioInput.uuidItemCardapio() ) );
        if ( !itemExisteNoCardapio ) {
            throw new ItemCardapioInexistenteException("O item com uuid " + itemCardapioInput.uuidItemCardapio() + " não existe no cardápio " + cardapio.getNome( ) );
        }

        this.cardapioInterface.removerItemDoCardapio( itemCardapioInput.uuidCardapio(), itemCardapioInput.uuidItemCardapio() );
    }
}

package br.com.fiap.techchallenge2.domain.usecase.itemcardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeletarItemCardapioUseCase
{

    private final ItemCardapioInterface itemCardapioInterface;
    private final CardapioInterface cardapioInterface;


    public void execute ( UUID uuidItemCardapio, UUID uuidCardapio, String tipoUsuarioLogado ) {

        if ( !tipoUsuarioLogado.equals( "DonoRestaurante" ) ) {
            throw new AcessoNegadoException( "Apenas usuários do tipo 'DonoRestaurante' podem deletar itens do cardápio" );
        }

        Cardapio cardapio = this.cardapioInterface.buscarCardapioPorUuid( uuidCardapio );
        if ( cardapio == null ) {
            throw new CardapioInexistenteException( "O cardápio com o uuid " + uuidCardapio + " a ser removido o item não existe" );
        }

        boolean itemExisteNoCardapio = cardapio.getItensCardapio().stream()
                .anyMatch(item -> item.getUuid( ).equals( uuidItemCardapio ) );
        if ( !itemExisteNoCardapio ) {
            throw new ItemCardapioJaExisteException( "O item com uuid " + uuidItemCardapio + " não existe no cardápio " + cardapio.getNome( ) );
        }

        this.cardapioInterface.removerItemDoCardapio( uuidCardapio, uuidItemCardapio );
        this.itemCardapioInterface.deletarItemCardapioPorUuid( uuidItemCardapio );
    }

}

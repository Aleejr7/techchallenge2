package br.com.fiap.techchallenge2.domain.exception.cardapio;

public class CardapioInexistenteException extends RuntimeException
{
    public CardapioInexistenteException( String message ) {
        super( message );
    }
}

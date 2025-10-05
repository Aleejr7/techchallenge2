package br.com.fiap.techchallenge2.domain.exception.itemcardapio;

public class ItemCardapioInexistenteException extends RuntimeException
{
    public ItemCardapioInexistenteException( String message ) {
        super( message );
    }
}

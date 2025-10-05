package br.com.fiap.techchallenge2.domain.exception.itemcardapio;

public class ItemCardapioJaExisteException extends RuntimeException
{
    public ItemCardapioJaExisteException( String message ) {
        super( message );
    }
}

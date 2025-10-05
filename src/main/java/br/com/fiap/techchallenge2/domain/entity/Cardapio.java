package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@Getter
public class Cardapio
{
    private UUID uuid;
    private String nome;
    @Setter
    private List<ItemCardapio> itensCardapio;

    public Cardapio( String nome )
    {
        validaNome( nome );
        this.nome = nome;
    }

    public void setNome( String nome )
    {
        validaNome( nome );
        this.nome = nome;
    }

    private void validaNome( String nome )
    {
        if( nome == null || nome.isBlank() )
        {
            throw new DadoVazioException( "Nome do cardápio não pode ser vazio" );
        }
    }
}

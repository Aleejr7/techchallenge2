package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Getter
public class TipoUsuario
{
    private UUID id;
    private String nome;

    public TipoUsuario(String nome) throws DadoVazioException
    {
        validaNome( nome );
        this.nome = nome;
    }

    public void setNome( String nome ){
        validaNome( nome );
        this.nome = nome;
    }

    private void validaNome( String nome ) throws DadoVazioException
    {
        if( nome.isEmpty() ){
            throw new DadoVazioException("Nome do tipo do usuário não pode ser vazio.");
        }
    }

}

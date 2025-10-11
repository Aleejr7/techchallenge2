package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.DadoInvalidoException;
import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import br.com.fiap.techchallenge2.domain.exception.disponibilidadePedido.DisponibilidadePedidoInexistenteException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
public class ItemCardapio
{

    @Setter
    private UUID uuid;
    private String nome;
    private String descricao;
    private Double preco;
    private DisponibilidadePedido disponibilidadePedido;
    private String imagemUrl;
    private UUID uuidCardapio;

    public ItemCardapio( String nome, String descricao, Double preco, DisponibilidadePedido disponibilidadePedido, String imagemUrl, UUID uuidCardapio ) {
        validaNome( nome );
        validaDescricao( descricao );
        validaPreco( preco );
        validaDisponibilidadePedido( disponibilidadePedido );
        validaImagemUrl( imagemUrl );

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadePedido = disponibilidadePedido;
        this.imagemUrl = imagemUrl;
        this.uuidCardapio = uuidCardapio;
    }

    public ItemCardapio(UUID uuid, String nome, String descricao, Double preco, DisponibilidadePedido disponibilidadePedido, String imagemUrl, UUID uuidCardapio ) {
        this(nome, descricao, preco, disponibilidadePedido, imagemUrl, uuidCardapio);
        this.uuid = uuid;
    }

    public ItemCardapio() {

    }

    public void setNome( String nome )
    {
        validaNome( nome );
        this.nome = nome;
    }

    public void setDescricao( String descricao )
    {
        validaDescricao( descricao );
        this.descricao = descricao;
    }

    public void setPreco( Double preco )
    {
        validaPreco( preco );
        this.preco = preco;
    }

    public void setDisponibilidadePedido( DisponibilidadePedido disponibilidadePedido )
    {
        validaDisponibilidadePedido( disponibilidadePedido );
        this.disponibilidadePedido = disponibilidadePedido;
    }

    public void setImagemUrl( String imagemUrl )
    {
        validaImagemUrl( imagemUrl );
        this.imagemUrl = imagemUrl;
    }

    private void validaNome( String nome ) {
        if ( nome == null || nome.isEmpty( ) ) {
            throw new DadoVazioException( "Nome do item do cardápio não pode ser vazio." );
        }
    }

    private void validaDescricao( String descricao ) {
        if ( descricao == null || descricao.isEmpty( ) ) {
            throw new DadoVazioException( "Descrição do item do cardápio não pode ser vazia." );
        }
    }

    private void validaPreco( Double preco ) {
        if( preco == null ) {
            throw new DadoVazioException( "Preço do item do cardápio não pode ser vazio." );
        }
        if ( preco <= 0 ) {
            throw new DadoInvalidoException( "Preço do item do cardápio deve ser maior que zero." );
        }
    }

    private void validaDisponibilidadePedido( DisponibilidadePedido disponibilidadePedido ) {
        if( disponibilidadePedido != DisponibilidadePedido.RESTAURANTE ) {
            throw new DisponibilidadePedidoInexistenteException( "Disponibilidade para pedido, deve ser somente no restaurante." );
        }

        if ( disponibilidadePedido == null ) {
            throw new DadoVazioException( "Disponibilidade do item do cardápio não pode ser vazia." );
        }
    }

    private void validaImagemUrl( String imagemUrl ) {
        if ( imagemUrl == null || imagemUrl.isEmpty( ) ) {
            throw new DadoVazioException( "URL da imagem do item do cardápio não pode ser vazia." );
        }
    }
}

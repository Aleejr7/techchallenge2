package br.com.fiap.techchallenge2.infra.model;

import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "itemCardapio")
@Getter
public class ItemCardapioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String nome;
    private String descricao;
    private Double preco;
    @Enumerated(EnumType.STRING)
    private DisponibilidadePedido disponibilidadePedido;
    private String imagemUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardapio_uuid")
    private UUID uuidCardapio;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setDisponibilidadePedido(DisponibilidadePedido disponibilidadePedido) {
        this.disponibilidadePedido = disponibilidadePedido;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public void setUuidCardapio(UUID uuidCardapio) {
        this.uuidCardapio = uuidCardapio;
    }

    public ItemCardapioModel(String nome, String descricao, Double preco, DisponibilidadePedido disponibilidadePedido, String imagemUrl, UUID uuidCardapio) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadePedido = disponibilidadePedido;
        this.imagemUrl = imagemUrl;
        this.uuidCardapio = uuidCardapio;
    }
}

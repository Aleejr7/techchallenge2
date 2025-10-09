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

}

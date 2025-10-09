package br.com.fiap.techchallenge2.infra.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cardapio")
@Getter
public class CardapioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String nome;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ItemCardapioModel> itensCardapio;
    private UUID uuidRestaurante;

}

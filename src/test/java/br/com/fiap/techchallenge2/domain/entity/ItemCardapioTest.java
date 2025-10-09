// src/test/java/br/com/fiap/techchallenge2/domain/entity/ItemCardapioTest.java
package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.DadoInvalidoException;
import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import br.com.fiap.techchallenge2.domain.exception.disponibilidadePedido.DisponibilidadePedidoInexistenteException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemCardapioTest {

    private UUID cardapioId = UUID.randomUUID();

    @Test
    void deveCriarItemCardapioComDadosValidos() {
        ItemCardapio item = new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        );
        assertEquals("Pizza", item.getNome());
        assertEquals("Pizza de queijo", item.getDescricao());
        assertEquals(35.0, item.getPreco());
        assertEquals(DisponibilidadePedido.RESTAURANTE, item.getDisponibilidadePedido());
        assertEquals("http://imagem.com/pizza.jpg", item.getImagemUrl());
        assertEquals(cardapioId, item.getUuidCardapio());
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        assertThrows(DadoVazioException.class, () -> new ItemCardapio(
                "",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        ));
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoVazia() {
        assertThrows(DadoVazioException.class, () -> new ItemCardapio(
                "Pizza",
                "",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        ));
    }

    @Test
    void deveLancarExcecaoQuandoPrecoNulo() {
        assertThrows(DadoVazioException.class, () -> new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                null,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        ));
    }

    @Test
    void deveLancarExcecaoQuandoPrecoMenorOuIgualZero() {
        assertThrows(DadoInvalidoException.class, () -> new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                0.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        ));
        assertThrows(DadoInvalidoException.class, () -> new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                -10.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        ));
    }

    @Test
    void deveLancarExcecaoQuandoDisponibilidadePedidoInvalida() {
        assertThrows(DisponibilidadePedidoInexistenteException.class, () -> new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.DELIVERY,
                "http://imagem.com/pizza.jpg",
                cardapioId
        ));
    }

    @Test
    void deveLancarExcecaoQuandoImagemUrlVazia() {
        assertThrows(DadoVazioException.class, () -> new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "",
                cardapioId
        ));
    }

    @Test
    void deveAlterarNomeComValorValido() {
        ItemCardapio item = new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        );
        item.setNome("Pizza Calabresa");
        assertEquals("Pizza Calabresa", item.getNome());
    }

    @Test
    void deveLancarExcecaoAoAlterarNomeParaVazio() {
        ItemCardapio item = new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        );
        assertThrows(DadoVazioException.class, () -> item.setNome(""));
    }

    @Test
    void deveAlterarPrecoComValorValido() {
        ItemCardapio item = new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        );
        item.setPreco(40.0);
        assertEquals(40.0, item.getPreco());
    }

    @Test
    void deveLancarExcecaoAoAlterarPrecoParaValorInvalido() {
        ItemCardapio item = new ItemCardapio(
                "Pizza",
                "Pizza de queijo",
                35.0,
                DisponibilidadePedido.RESTAURANTE,
                "http://imagem.com/pizza.jpg",
                cardapioId
        );
        assertThrows(DadoInvalidoException.class, () -> item.setPreco(0.0));
    }
}
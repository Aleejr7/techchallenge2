// src/test/java/br/com/fiap/techchallenge2/domain/entity/CardapioTest.java
package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardapioTest {

    @Test
    void deveCriarCardapioComNomeValido() {
        UUID restauranteId = UUID.randomUUID();
        Cardapio cardapio = new Cardapio("Cardápio Principal", restauranteId);
        assertEquals("Cardápio Principal", cardapio.getNome());
        assertEquals(restauranteId, cardapio.getUuidRestaurante());
        assertNull(cardapio.getItensCardapio());
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazioNoConstrutor() {
        UUID restauranteId = UUID.randomUUID();
        assertThrows(DadoVazioException.class, () -> new Cardapio("", restauranteId));
    }

    @Test
    void deveLancarExcecaoQuandoNomeNuloNoConstrutor() {
        UUID restauranteId = UUID.randomUUID();
        assertThrows(DadoVazioException.class, () -> new Cardapio(null, restauranteId));
    }

    @Test
    void deveAlterarNomeComValorValido() {
        Cardapio cardapio = new Cardapio("Cardápio 1", UUID.randomUUID());
        cardapio.setNome("Cardápio 2");
        assertEquals("Cardápio 2", cardapio.getNome());
    }

    @Test
    void deveLancarExcecaoAoAlterarNomeParaVazio() {
        Cardapio cardapio = new Cardapio("Cardápio 1", UUID.randomUUID());
        assertThrows(DadoVazioException.class, () -> cardapio.setNome(""));
    }

    @Test
    void deveLancarExcecaoAoAlterarNomeParaNulo() {
        Cardapio cardapio = new Cardapio("Cardápio 1", UUID.randomUUID());
        assertThrows(DadoVazioException.class, () -> cardapio.setNome(null));
    }

    @Test
    void deveAlterarItensCardapio() {
        Cardapio cardapio = new Cardapio("Cardápio 1", UUID.randomUUID());
        ItemCardapio item = new ItemCardapio(); // Supondo construtor padrão
        cardapio.setItensCardapio(List.of(item));
        assertEquals(1, cardapio.getItensCardapio().size());
        assertEquals(item, cardapio.getItensCardapio().get(0));
    }
}
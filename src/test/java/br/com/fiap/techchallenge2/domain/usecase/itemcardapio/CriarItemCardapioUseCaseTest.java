package br.com.fiap.techchallenge2.domain.usecase.itemcardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.CriarItemCardapioInput;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.ItemCardapioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CriarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioInterface itemCardapioInterface;
    @Mock
    private CardapioInterface cardapioInterface;

    private CriarItemCardapioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new CriarItemCardapioUseCase(itemCardapioInterface, cardapioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoForDonoRestaurante() {
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                "Pizza Margherita",
                "Pizza com molho de tomate e queijo",
                35.90,
                "https://exemplo.com/pizza.jpg",
                UUID.randomUUID(),
                "Cliente"
        );

        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(cardapioInterface, never()).buscarCardapioPorUuid(any());
        verify(itemCardapioInterface, never()).criarItemCardapio(any());
    }

    @Test
    void deveLancarExcecaoQuandoCardapioNaoExiste() {
        UUID uuidCardapio = UUID.randomUUID();
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                "Pizza Margherita",
                "Pizza com molho de tomate e queijo",
                35.90,
                "https://exemplo.com/pizza.jpg",
                uuidCardapio,
                "DonoRestaurante"
        );

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(null);

        assertThrows(CardapioInexistenteException.class, () -> useCase.execute(input));
        verify(itemCardapioInterface, never()).criarItemCardapio(any());
    }

    @Test
    void deveLancarExcecaoQuandoItemJaExisteNoCardapio() {
        UUID uuidCardapio = UUID.randomUUID();
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                "Pizza Margherita",
                "Pizza com molho de tomate e queijo",
                35.90,
                "https://exemplo.com/pizza.jpg",
                uuidCardapio,
                "DonoRestaurante"
        );

        Cardapio cardapio = mock(Cardapio.class);
        ItemCardapio itemExistente = mock(ItemCardapio.class);

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(cardapio);
        when(cardapio.getItensCardapio()).thenReturn(Arrays.asList(itemExistente));
        when(itemExistente.getNome()).thenReturn("Pizza Margherita");
        when(cardapio.getNome()).thenReturn("Cardápio Principal");

        assertThrows(ItemCardapioJaExisteException.class, () -> useCase.execute(input));
        verify(itemCardapioInterface, never()).criarItemCardapio(any());
    }

    // @Test
    void deveCriarItemCardapioComSucesso() {
        UUID uuidCardapio = UUID.randomUUID();
        UUID uuidItem = UUID.randomUUID();
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                "Pizza Margherita",
                "Pizza com molho de tomate e queijo",
                35.90,
                "https://exemplo.com/pizza.jpg",
                uuidCardapio,
                "DonoRestaurante"
        );

        Cardapio cardapio = mock(Cardapio.class);
        ItemCardapio itemCriado = mock(ItemCardapio.class);

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(cardapio);
        when(cardapio.getItensCardapio()).thenReturn(Collections.emptyList());
        when(cardapio.getUuid()).thenReturn(uuidCardapio);
        when(itemCardapioInterface.criarItemCardapio(any(ItemCardapio.class))).thenReturn(itemCriado);

        when(itemCriado.getUuid()).thenReturn(uuidItem);
        when(itemCriado.getNome()).thenReturn("Pizza Margherita");
        when(itemCriado.getDescricao()).thenReturn("Pizza com molho de tomate e queijo");
        when(itemCriado.getPreco()).thenReturn(35.90);
        when(itemCriado.getDisponibilidadePedido()).thenReturn(DisponibilidadePedido.RESTAURANTE);
        when(itemCriado.getImagemUrl()).thenReturn("https://exemplo.com/pizza.jpg");

        ItemCardapioOutput resultado = useCase.execute(input);

        assertNotNull(resultado);
        assertEquals(uuidItem, resultado.uuid());
        assertEquals("Pizza Margherita", resultado.nome());
        assertEquals("Pizza com molho de tomate e queijo", resultado.descricao());
        assertEquals(35.90, resultado.preco());
        assertNotNull(resultado.disponibilidadePedido());
        assertEquals("https://exemplo.com/pizza.jpg", resultado.imagemUrl());
        assertEquals(uuidCardapio, resultado.uuidCardapio());

        verify(itemCardapioInterface).criarItemCardapio(any(ItemCardapio.class));
        verify(cardapioInterface).adicionarItemAoCardapio(uuidCardapio, uuidItem);
    }
}

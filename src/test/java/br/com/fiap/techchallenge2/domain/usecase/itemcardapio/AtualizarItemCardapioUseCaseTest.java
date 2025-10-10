package br.com.fiap.techchallenge2.domain.usecase.itemcardapio;

import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.AtualizarItemCardapioInput;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.AtualizarItemCardapioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AtualizarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioInterface itemCardapioInterface;

    private AtualizarItemCardapioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AtualizarItemCardapioUseCase(itemCardapioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoForDonoRestaurante() {
        AtualizarItemCardapioInput input = new AtualizarItemCardapioInput(
                UUID.randomUUID(),
                "Pizza Margherita",
                "Pizza com molho de tomate e queijo",
                35.90,
                "https://exemplo.com/pizza.jpg",
                "Cliente"
        );

        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(itemCardapioInterface, never()).buscarItemCardapioPorUuid(any());
        verify(itemCardapioInterface, never()).atualizarItemCardapio(any());
    }

    @Test
    void deveLancarExcecaoQuandoItemCardapioNaoExiste() {
        UUID uuidItem = UUID.randomUUID();
        AtualizarItemCardapioInput input = new AtualizarItemCardapioInput(
                uuidItem,
                "Pizza Margherita",
                "Pizza com molho de tomate e queijo",
                35.90,
                "https://exemplo.com/pizza.jpg",
                "DonoRestaurante"
        );

        when(itemCardapioInterface.buscarItemCardapioPorUuid(uuidItem)).thenReturn(null);

        assertThrows(ItemCardapioInexistenteException.class, () -> useCase.execute(input));
        verify(itemCardapioInterface, never()).atualizarItemCardapio(any());
    }

    @Test
    void deveAtualizarItemCardapioComSucesso() {
        UUID uuidItem = UUID.randomUUID();
        AtualizarItemCardapioInput input = new AtualizarItemCardapioInput(
                uuidItem,
                "Pizza Margherita Atualizada",
                "Pizza com molho de tomate, queijo mussarela e manjericão",
                42.50,
                "https://exemplo.com/pizza-nova.jpg",
                "DonoRestaurante"
        );

        ItemCardapio itemExistente = mock(ItemCardapio.class);
        ItemCardapio itemAtualizado = mock(ItemCardapio.class);

        when(itemCardapioInterface.buscarItemCardapioPorUuid(uuidItem)).thenReturn(itemExistente);
        when(itemCardapioInterface.atualizarItemCardapio(itemExistente)).thenReturn(itemAtualizado);

        when(itemAtualizado.getUuid()).thenReturn(uuidItem);
        when(itemAtualizado.getNome()).thenReturn("Pizza Margherita Atualizada");
        when(itemAtualizado.getDescricao()).thenReturn("Pizza com molho de tomate, queijo mussarela e manjericão");
        when(itemAtualizado.getPreco()).thenReturn(42.50);
        when(itemAtualizado.getDisponibilidadePedido()).thenReturn(DisponibilidadePedido.RESTAURANTE);
        when(itemAtualizado.getImagemUrl()).thenReturn("https://exemplo.com/pizza-nova.jpg");

        AtualizarItemCardapioOutput resultado = useCase.execute(input);

        assertNotNull(resultado);
        assertEquals(uuidItem, resultado.uuid());
        assertEquals("Pizza Margherita Atualizada", resultado.nome());
        assertEquals("Pizza com molho de tomate, queijo mussarela e manjericão", resultado.descricao());
        assertEquals(42.50, resultado.preco());
        assertNotNull(resultado.disponibilidadePedido());
        assertEquals("https://exemplo.com/pizza-nova.jpg", resultado.imagemUrl());

        verify(itemExistente).setNome("Pizza Margherita Atualizada");
        verify(itemExistente).setDescricao("Pizza com molho de tomate, queijo mussarela e manjericão");
        verify(itemExistente).setPreco(42.50);
        verify(itemExistente).setImagemUrl("https://exemplo.com/pizza-nova.jpg");
        verify(itemCardapioInterface).atualizarItemCardapio(itemExistente);
    }
}

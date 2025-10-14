package br.com.fiap.techchallenge2.domain.usecase.itemcardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.DeletarItemCardapioInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeletarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioInterface itemCardapioInterface;
    @Mock
    private CardapioInterface cardapioInterface;

    private DeletarItemCardapioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DeletarItemCardapioUseCase(itemCardapioInterface, cardapioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoForDonoRestaurante() {
        DeletarItemCardapioInput input = new DeletarItemCardapioInput(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Cliente"
        );

        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(cardapioInterface, never()).buscarCardapioPorUuid(any());
        verify(itemCardapioInterface, never()).deletarItemCardapioPorUuid(any());
    }

    @Test
    void deveLancarExcecaoQuandoCardapioNaoExiste() {
        UUID uuidCardapio = UUID.randomUUID();
        DeletarItemCardapioInput input = new DeletarItemCardapioInput(
                UUID.randomUUID(),
                uuidCardapio,
                "DonoRestaurante"
        );

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(null);

        assertThrows(CardapioInexistenteException.class, () -> useCase.execute(input));
        verify(itemCardapioInterface, never()).deletarItemCardapioPorUuid(any());
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoExisteNoCardapio() {
        UUID uuidCardapio = UUID.randomUUID();
        UUID uuidItemCardapio = UUID.randomUUID();
        UUID uuidItemExistente = UUID.randomUUID();

        DeletarItemCardapioInput input = new DeletarItemCardapioInput(
                uuidItemCardapio,
                uuidCardapio,
                "DonoRestaurante"
        );

        Cardapio cardapio = mock(Cardapio.class);
        ItemCardapio itemExistente = mock(ItemCardapio.class);

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(cardapio);
        when(cardapio.getItensCardapio()).thenReturn(Arrays.asList(itemExistente));
        when(itemExistente.getUuid()).thenReturn(uuidItemExistente);
        when(cardapio.getNome()).thenReturn("Cardápio Principal");

        assertThrows(ItemCardapioInexistenteException.class, () -> useCase.execute(input));
        verify(itemCardapioInterface, never()).deletarItemCardapioPorUuid(any());
    }

    @Test
    void deveDeletarItemCardapioComSucesso() {
        UUID uuidCardapio = UUID.randomUUID();
        UUID uuidItemCardapio = UUID.randomUUID();

        DeletarItemCardapioInput input = new DeletarItemCardapioInput(
                uuidItemCardapio,
                uuidCardapio,
                "DonoRestaurante"
        );

        Cardapio cardapio = mock(Cardapio.class);
        ItemCardapio itemExistente = mock(ItemCardapio.class);

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(cardapio);
        when(cardapio.getItensCardapio()).thenReturn(Arrays.asList(itemExistente));
        when(itemExistente.getUuid()).thenReturn(uuidItemCardapio);

        assertDoesNotThrow(() -> useCase.execute(input));

        verify(cardapioInterface).removerItemDoCardapio(uuidCardapio, uuidItemCardapio);
        verify(itemCardapioInterface).deletarItemCardapioPorUuid(uuidItemCardapio);
    }
}

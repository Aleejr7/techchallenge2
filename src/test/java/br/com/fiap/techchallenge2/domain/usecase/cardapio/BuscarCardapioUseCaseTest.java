package br.com.fiap.techchallenge2.domain.usecase.cardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.output.cardapio.CardapioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarCardapioUseCaseTest {

    @Mock
    private CardapioInterface cardapioInterface;

    private BuscarCardapioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarCardapioUseCase(cardapioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoCardapioNaoExiste() {
        UUID uuidCardapio = UUID.randomUUID();
        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(null);

        assertThrows(CardapioInexistenteException.class, () -> useCase.execute(uuidCardapio));
    }

    @Test
    void deveRetornarCardapioQuandoExiste() {
        UUID uuidCardapio = UUID.randomUUID();
        UUID uuidItem1 = UUID.randomUUID();
        UUID uuidItem2 = UUID.randomUUID();

        Cardapio cardapio = mock(Cardapio.class);
        ItemCardapio item1 = mock(ItemCardapio.class);
        ItemCardapio item2 = mock(ItemCardapio.class);

        when(cardapio.getUuid()).thenReturn(uuidCardapio);
        when(cardapio.getNome()).thenReturn("Cardápio Principal");
        when(cardapio.getItensCardapio()).thenReturn(Arrays.asList(item1, item2));

        when(item1.getUuid()).thenReturn(uuidItem1);
        when(item1.getNome()).thenReturn("Pizza Margherita");
        when(item1.getDescricao()).thenReturn("Pizza com molho de tomate e queijo");
        when(item1.getPreco()).thenReturn(35.90);
        when(item1.getDisponibilidadePedido()).thenReturn(DisponibilidadePedido.RESTAURANTE);
        when(item1.getImagemUrl()).thenReturn("https://exemplo.com/pizza.jpg");

        when(item2.getUuid()).thenReturn(uuidItem2);
        when(item2.getNome()).thenReturn("Lasanha Bolonhesa");
        when(item2.getDescricao()).thenReturn("Lasanha com molho bolonhesa");
        when(item2.getPreco()).thenReturn(42.50);
        when(item2.getDisponibilidadePedido()).thenReturn(DisponibilidadePedido.RESTAURANTE);
        when(item2.getImagemUrl()).thenReturn("https://exemplo.com/lasanha.jpg");

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(cardapio);

        CardapioOutput resultado = useCase.execute(uuidCardapio);

        assertNotNull(resultado);
        assertEquals(uuidCardapio, resultado.uuid());
        assertEquals("Cardápio Principal", resultado.nome());
        assertNotNull(resultado.itensCardapio());
        assertEquals(2, resultado.itensCardapio().size());

        assertEquals(uuidItem1, resultado.itensCardapio().get(0).uuid());
        assertEquals("Pizza Margherita", resultado.itensCardapio().get(0).nome());
        assertEquals("Pizza com molho de tomate e queijo", resultado.itensCardapio().get(0).descricao());
        assertEquals(35.90, resultado.itensCardapio().get(0).preco());
        assertEquals("https://exemplo.com/pizza.jpg", resultado.itensCardapio().get(0).imagemUrl());
        assertEquals(uuidCardapio, resultado.itensCardapio().get(0).uuidCardapio());

        assertEquals(uuidItem2, resultado.itensCardapio().get(1).uuid());
        assertEquals("Lasanha Bolonhesa", resultado.itensCardapio().get(1).nome());
        assertEquals("Lasanha com molho bolonhesa", resultado.itensCardapio().get(1).descricao());
        assertEquals(42.50, resultado.itensCardapio().get(1).preco());
        assertEquals("https://exemplo.com/lasanha.jpg", resultado.itensCardapio().get(1).imagemUrl());
        assertEquals(uuidCardapio, resultado.itensCardapio().get(1).uuidCardapio());

        verify(cardapioInterface).buscarCardapioPorUuid(uuidCardapio);
    }
}

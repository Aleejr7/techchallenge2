package br.com.fiap.techchallenge2.domain.usecase.cardapio;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.cardapio.CardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.input.cardapio.AlterarNomeCardapioInput;
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

public class AlterarNomeCardapioUseCaseTest {

    @Mock
    private CardapioInterface cardapioInterface;

    private AlterarNomeCardapioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AlterarNomeCardapioUseCase(cardapioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoForDonoRestaurante() {
        AlterarNomeCardapioInput input = new AlterarNomeCardapioInput(
                UUID.randomUUID(),
                "Novo Nome",
                "Cliente"
        );

        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(cardapioInterface, never()).buscarCardapioPorUuid(any());
        verify(cardapioInterface, never()).atualizarCardapio(any());
    }

    @Test
    void deveLancarExcecaoQuandoCardapioNaoExiste() {
        UUID uuidCardapio = UUID.randomUUID();
        AlterarNomeCardapioInput input = new AlterarNomeCardapioInput(
                uuidCardapio,
                "Novo Nome",
                "DonoRestaurante"
        );

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(null);

        assertThrows(CardapioInexistenteException.class, () -> useCase.execute(input));
        verify(cardapioInterface, never()).atualizarCardapio(any());
    }

    @Test
    void deveAlterarNomeCardapioComSucesso() {
        UUID uuidCardapio = UUID.randomUUID();
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidItem = UUID.randomUUID();

        AlterarNomeCardapioInput input = new AlterarNomeCardapioInput(
                uuidCardapio,
                "Cardápio Atualizado",
                "DonoRestaurante"
        );

        Cardapio cardapioExistente = mock(Cardapio.class);
        Cardapio cardapioAtualizado = mock(Cardapio.class);
        ItemCardapio itemCardapio = mock(ItemCardapio.class);

        when(cardapioInterface.buscarCardapioPorUuid(uuidCardapio)).thenReturn(cardapioExistente);
        when(cardapioInterface.atualizarCardapio(cardapioExistente)).thenReturn(cardapioAtualizado);

        when(cardapioAtualizado.getUuid()).thenReturn(uuidCardapio);
        when(cardapioAtualizado.getNome()).thenReturn("Cardápio Atualizado");
        when(cardapioAtualizado.getItensCardapio()).thenReturn(Arrays.asList(itemCardapio));

        when(itemCardapio.getUuid()).thenReturn(uuidItem);
        when(itemCardapio.getNome()).thenReturn("Item Teste");
        when(itemCardapio.getDescricao()).thenReturn("Descrição do item");
        when(itemCardapio.getPreco()).thenReturn(25.90);
        when(itemCardapio.getDisponibilidadePedido()).thenReturn(DisponibilidadePedido.RESTAURANTE);
        when(itemCardapio.getImagemUrl()).thenReturn("https://exemplo.com/imagem.jpg");

        CardapioOutput resultado = useCase.execute(input);

        assertNotNull(resultado);
        assertEquals(uuidCardapio, resultado.uuid());
        assertEquals("Cardápio Atualizado", resultado.nome());
        assertNotNull(resultado.itensCardapio());
        assertEquals(1, resultado.itensCardapio().size());
        assertEquals(uuidItem, resultado.itensCardapio().get(0).uuid());
        assertEquals("Item Teste", resultado.itensCardapio().get(0).nome());
        assertEquals("Descrição do item", resultado.itensCardapio().get(0).descricao());
        assertEquals(25.90, resultado.itensCardapio().get(0).preco());
        assertEquals("https://exemplo.com/imagem.jpg", resultado.itensCardapio().get(0).imagemUrl());
        assertEquals(uuidCardapio, resultado.itensCardapio().get(0).uuidCardapio());

        verify(cardapioExistente).setNome("Cardápio Atualizado");
        verify(cardapioInterface).atualizarCardapio(cardapioExistente);
    }
}

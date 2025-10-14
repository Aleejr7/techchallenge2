package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.ItemCardapioRequest;
import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.exception.itemcardapio.ItemCardapioInexistenteException;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.ItemCardapioOutput;
import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioControllerTest {

    @Mock
    private CardapioModelRepository cardapioRepository;

    @Mock
    private ItemCardapioModelRepository itemCardapioRepository;

    private ItemCardapioController itemCardapioController;

    private UUID cardapioUuid;
    private UUID itemUuid;
    private final String TIPO_USUARIO_LOGADO = "DonoRestaurante";

    @BeforeEach
    void setUp() {
        itemCardapioController = new ItemCardapioController(cardapioRepository, itemCardapioRepository);
        cardapioUuid = UUID.randomUUID();
        itemUuid = UUID.randomUUID();
    }

    @Test
    void deveCriarItemComSucesso() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        ItemCardapioRequest request = new ItemCardapioRequest(null, "Hamburguer", "Delicioso", 25.50, null, "imagem.png", cardapioUuid);
        CardapioModel cardapioModelMock = mock(CardapioModel.class);

        when(cardapioModelMock.getNome()).thenReturn("Cardapio Principal");
        when(cardapioModelMock.getUuid()).thenReturn(cardapioUuid);
        when(cardapioRepository.findById(cardapioUuid)).thenReturn(Optional.of(cardapioModelMock));


        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ItemCardapioModel modelPassadoParaSalvar = (ItemCardapioModel) args[0];

            Field uuidField = ItemCardapioModel.class.getDeclaredField("uuid");
            uuidField.setAccessible(true);
            uuidField.set(modelPassadoParaSalvar, itemUuid);

            return modelPassadoParaSalvar;
        }).when(itemCardapioRepository).save(any(ItemCardapioModel.class));


        // Act
        ResponseEntity<ItemCardapioOutput> response = itemCardapioController.criarItemCardapio(request, TIPO_USUARIO_LOGADO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(itemUuid, response.getBody().uuid());
    }

    @Test
    void deveAtualizarItemComSucesso() {
        // Arrange
        ItemCardapioRequest request = new ItemCardapioRequest(null, "Hamburguer Especial", "Mais que delicioso", 30.00, null, "imagem2.png", null);
        ItemCardapioModel itemExistenteModel = mock(ItemCardapioModel.class);
        CardapioModel cardapioModelMock = mock(CardapioModel.class);

        when(itemExistenteModel.getUuid()).thenReturn(itemUuid);

        when(itemExistenteModel.getNome()).thenReturn("Nome Antigo");
        when(itemExistenteModel.getDescricao()).thenReturn("Desc Antiga");
        when(itemExistenteModel.getPreco()).thenReturn(20.0);
        when(itemExistenteModel.getDisponibilidadePedido()).thenReturn(DisponibilidadePedido.RESTAURANTE);
        when(itemExistenteModel.getImagemUrl()).thenReturn("img.png");
        when(itemExistenteModel.getCardapioModel()).thenReturn(cardapioModelMock);
        when(cardapioModelMock.getUuid()).thenReturn(cardapioUuid);

        when(itemCardapioRepository.findById(itemUuid)).thenReturn(Optional.of(itemExistenteModel));
        when(itemCardapioRepository.save(any(ItemCardapioModel.class))).thenReturn(itemExistenteModel);

        // Act
        ResponseEntity<ItemCardapioOutput> response = itemCardapioController.atualizarItemCardapio(itemUuid, request, TIPO_USUARIO_LOGADO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveDeletarItemComSucesso() {
        // Arrange
        ItemCardapioRequest request = new ItemCardapioRequest(null, null, null, null, null, null, cardapioUuid);
        CardapioModel cardapioModelMock = mock(CardapioModel.class);

        when(cardapioRepository.findById(cardapioUuid)).thenReturn(Optional.of(cardapioModelMock));
        when(cardapioModelMock.getNome()).thenReturn("Cardapio Principal");

        assertThrows(ItemCardapioInexistenteException.class, () -> {
            itemCardapioController.deletarItemCardapio(itemUuid, TIPO_USUARIO_LOGADO, request);
        });


        verify(cardapioRepository, times(1)).findById(cardapioUuid);
        verify(itemCardapioRepository, never()).findByCardapioModelUuid(any());
        verify(itemCardapioRepository, never()).deleteById(any());
    }
}
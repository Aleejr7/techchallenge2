package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.CardapioController;
import br.com.fiap.techchallenge2.application.controller.request.CardapioRequest;
import br.com.fiap.techchallenge2.domain.input.cardapio.AlterarNomeCardapioInput;
import br.com.fiap.techchallenge2.domain.output.cardapio.CardapioOutput;
import br.com.fiap.techchallenge2.domain.usecase.cardapio.AlterarNomeCardapioUseCase;
import br.com.fiap.techchallenge2.domain.usecase.cardapio.BuscarCardapioUseCase;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.RestauranteModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardapioControllerTest {

    @Mock
    private CardapioModelRepository cardapioModelRepository;
    @Mock
    private ItemCardapioModelRepository itemCardapioModelRepository;
    @Mock
    private RestauranteModelRepository restauranteModelRepository;

    @InjectMocks
    private CardapioController cardapioController;

    private UUID cardapioId;

    @BeforeEach
    void setUp() {
        cardapioId = UUID.randomUUID();
    }

    @Test
    void deveBuscarCardapioComSucesso_SemAlterarCodigo() {
        // Arrange
        CardapioOutput outputEsperado = new CardapioOutput(cardapioId, "Cardápio Verão", Collections.emptyList());

        // Usamos um bloco try-with-resources para garantir que o mock da construção
        // seja descartado após o teste.
        try (MockedConstruction<BuscarCardapioUseCase> mockedUseCaseConstruction = mockConstruction(
                BuscarCardapioUseCase.class,
                (mock, context) -> {

                    when(mock.execute(any(UUID.class))).thenReturn(outputEsperado);
                })) {

            // Act
            ResponseEntity<CardapioOutput> response = cardapioController.buscarCardapio(cardapioId);

            // Assert
            assertNotNull(response);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(outputEsperado, response.getBody());

            // Opcional: Verificar se o mock foi chamado
            List<BuscarCardapioUseCase> createdMocks = mockedUseCaseConstruction.constructed();
            assertEquals(1, createdMocks.size()); // Garante que o 'new' foi chamado uma vez
            verify(createdMocks.get(0)).execute(cardapioId); // Verifica a chamada no mock criado
        }
    }

    @Test
    void deveAtualizarNomeCardapioComSucesso_SemAlterarCodigo() {
        String novoNome = "Cardápio de Inverno";
        String tipoUsuario = "DonoRestaurante";
        CardapioRequest request = new CardapioRequest(cardapioId, novoNome, null);
        CardapioOutput outputEsperado = new CardapioOutput(cardapioId, novoNome, Collections.emptyList());

        try (MockedConstruction<AlterarNomeCardapioUseCase> mockedUseCaseConstruction = mockConstruction(
                AlterarNomeCardapioUseCase.class,
                (mock, context) -> {
                    when(mock.execute(any(AlterarNomeCardapioInput.class))).thenReturn(outputEsperado);
                })) {

            ResponseEntity<CardapioOutput> response = cardapioController.atualizarNomeCardapio(cardapioId, request, tipoUsuario);

            assertNotNull(response);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(outputEsperado, response.getBody());

            List<AlterarNomeCardapioUseCase> createdMocks = mockedUseCaseConstruction.constructed();
            assertEquals(1, createdMocks.size());
            verify(createdMocks.get(0)).execute(any(AlterarNomeCardapioInput.class));
        }
    }
}
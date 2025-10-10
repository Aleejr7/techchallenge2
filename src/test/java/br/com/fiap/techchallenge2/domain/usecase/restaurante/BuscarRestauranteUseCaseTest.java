package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.HorarioFuncionamento;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.output.restaurante.BuscarRestauranteOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarRestauranteUseCaseTest {

    @Mock
    private RestauranteInterface restauranteInterface;

    private BuscarRestauranteUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarRestauranteUseCase(restauranteInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExiste() {
        UUID uuidRestaurante = UUID.randomUUID();
        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(null);

        assertThrows(RestauranteInexistenteException.class, () -> useCase.execute(uuidRestaurante));
    }

    @Test
    void deveRetornarRestauranteQuandoExiste() {
        UUID uuidRestaurante = UUID.randomUUID();
        Restaurante restaurante = mock(Restaurante.class);
        HorarioFuncionamento horarioFuncionamento = mock(HorarioFuncionamento.class);

        when(restaurante.getUuid()).thenReturn(uuidRestaurante);
        when(restaurante.getNome()).thenReturn("Restaurante Italiano");
        when(restaurante.getEndereco()).thenReturn("Rua A, 123");
        when(restaurante.getTipoCozinha()).thenReturn("Italiana");
        when(restaurante.getHorarioFuncionamento()).thenReturn(horarioFuncionamento);
        when(horarioFuncionamento.horarioAbertura()).thenReturn(LocalTime.of(11, 0));
        when(horarioFuncionamento.horarioFechamento()).thenReturn(LocalTime.of(23, 0));

        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(restaurante);

        BuscarRestauranteOutput resultado = useCase.execute(uuidRestaurante);

        assertNotNull(resultado);
        assertEquals(uuidRestaurante, resultado.uuid());
        assertEquals("Restaurante Italiano", resultado.nome());
        assertEquals("Rua A, 123", resultado.endereco());
        assertEquals("Italiana", resultado.tipoCozinha());
        assertEquals("11:00", resultado.horarioAbertura());
        assertEquals("23:00", resultado.horarioFechamento());

        verify(restauranteInterface).buscarRestaurantePorUuid(uuidRestaurante);
    }
}

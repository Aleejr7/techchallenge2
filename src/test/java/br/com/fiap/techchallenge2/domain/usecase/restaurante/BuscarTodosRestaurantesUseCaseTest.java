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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarTodosRestaurantesUseCaseTest {

    @Mock
    private RestauranteInterface restauranteInterface;

    private BuscarTodosRestaurantesUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarTodosRestaurantesUseCase(restauranteInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoNaoExistirRestaurantes() {
        when(restauranteInterface.buscarTodosRestaurantes()).thenReturn(null);

        assertThrows(RestauranteInexistenteException.class, () -> useCase.execute());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverRestaurantes() {
        when(restauranteInterface.buscarTodosRestaurantes()).thenReturn(Arrays.asList());

        List<BuscarRestauranteOutput> resultado = useCase.execute();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveRetornarTodosRestaurantesComSucesso() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        Restaurante restaurante1 = mock(Restaurante.class);
        Restaurante restaurante2 = mock(Restaurante.class);
        HorarioFuncionamento horario1 = mock(HorarioFuncionamento.class);
        HorarioFuncionamento horario2 = mock(HorarioFuncionamento.class);

        when(restaurante1.getUuid()).thenReturn(uuid1);
        when(restaurante1.getNome()).thenReturn("Restaurante Italiano");
        when(restaurante1.getEndereco()).thenReturn("Rua A, 123");
        when(restaurante1.getTipoCozinha()).thenReturn("Italiana");
        when(restaurante1.getHorarioFuncionamento()).thenReturn(horario1);
        when(horario1.horarioAbertura()).thenReturn(LocalTime.of(11, 0));
        when(horario1.horarioFechamento()).thenReturn(LocalTime.of(23, 0));

        when(restaurante2.getUuid()).thenReturn(uuid2);
        when(restaurante2.getNome()).thenReturn("Restaurante Japonês");
        when(restaurante2.getEndereco()).thenReturn("Rua B, 456");
        when(restaurante2.getTipoCozinha()).thenReturn("Japonesa");
        when(restaurante2.getHorarioFuncionamento()).thenReturn(horario2);
        when(horario2.horarioAbertura()).thenReturn(LocalTime.of(18, 0));
        when(horario2.horarioFechamento()).thenReturn(LocalTime.of(22, 30));

        List<Restaurante> restaurantes = Arrays.asList(restaurante1, restaurante2);
        when(restauranteInterface.buscarTodosRestaurantes()).thenReturn(restaurantes);

        List<BuscarRestauranteOutput> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        BuscarRestauranteOutput output1 = resultado.get(0);
        assertEquals(uuid1, output1.uuid());
        assertEquals("Restaurante Italiano", output1.nome());
        assertEquals("Rua A, 123", output1.endereco());
        assertEquals("Italiana", output1.tipoCozinha());
        assertEquals("11:00", output1.horarioAbertura());
        assertEquals("23:00", output1.horarioFechamento());

        BuscarRestauranteOutput output2 = resultado.get(1);
        assertEquals(uuid2, output2.uuid());
        assertEquals("Restaurante Japonês", output2.nome());
        assertEquals("Rua B, 456", output2.endereco());
        assertEquals("Japonesa", output2.tipoCozinha());
        assertEquals("18:00", output2.horarioAbertura());
        assertEquals("22:30", output2.horarioFechamento());

        verify(restauranteInterface).buscarTodosRestaurantes();
    }
}

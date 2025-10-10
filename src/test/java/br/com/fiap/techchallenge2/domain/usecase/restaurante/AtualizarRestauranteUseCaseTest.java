package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.entity.HorarioFuncionamento;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.restaurante.AtualizarRestauranteInput;
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

public class AtualizarRestauranteUseCaseTest {

    @Mock
    private RestauranteInterface restauranteInterface;
    @Mock
    private UsuarioInterface usuarioInterface;

    private AtualizarRestauranteUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AtualizarRestauranteUseCase(restauranteInterface, usuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExiste() {
        AtualizarRestauranteInput input = mock(AtualizarRestauranteInput.class);
        UUID uuidRestaurante = UUID.randomUUID();
        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(null);

        assertThrows(RestauranteInexistenteException.class, () -> useCase.execute(input));
        verify(restauranteInterface, never()).atualizarRestaurante(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioDonoNaoExiste() {
        AtualizarRestauranteInput input = mock(AtualizarRestauranteInput.class);
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidDono = UUID.randomUUID();
        Restaurante restaurante = mock(Restaurante.class);

        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(restaurante);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(null);

        assertThrows(UsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(restauranteInterface, never()).atualizarRestaurante(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForDonoDoRestaurante() {
        AtualizarRestauranteInput input = mock(AtualizarRestauranteInput.class);
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidDono = UUID.randomUUID();
        UUID uuidOutroUsuario = UUID.randomUUID();

        Restaurante restaurante = mock(Restaurante.class);
        Usuario donoRestaurante = mock(Usuario.class);
        Usuario outroUsuario = mock(Usuario.class);

        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(input.uuidDonoRestaurante()).thenReturn(uuidOutroUsuario);
        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(restaurante);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidOutroUsuario)).thenReturn(outroUsuario);
        when(restaurante.getDonoRestaurante()).thenReturn(donoRestaurante);
        when(donoRestaurante.getUuid()).thenReturn(uuidDono);
        when(outroUsuario.getUuid()).thenReturn(uuidOutroUsuario);

        assertThrows(RestauranteInexistenteException.class, () -> useCase.execute(input));
        verify(restauranteInterface, never()).atualizarRestaurante(any());
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        AtualizarRestauranteInput input = mock(AtualizarRestauranteInput.class);
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidDono = UUID.randomUUID();

        Restaurante restaurante = mock(Restaurante.class);
        Usuario donoRestaurante = mock(Usuario.class);
        Restaurante restauranteAtualizado = mock(Restaurante.class);
        HorarioFuncionamento horarioFuncionamento = mock(HorarioFuncionamento.class);

        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(input.nome()).thenReturn("Restaurante Atualizado");
        when(input.endereco()).thenReturn("Nova Rua, 456");
        when(input.tipoCozinha()).thenReturn("Chinesa");
        when(input.horarioAbertura()).thenReturn("12:00");
        when(input.horarioFechamento()).thenReturn("23:30");

        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(restaurante);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(donoRestaurante);
        when(restaurante.getDonoRestaurante()).thenReturn(donoRestaurante);
        when(donoRestaurante.getUuid()).thenReturn(uuidDono);

        when(restauranteInterface.atualizarRestaurante(restaurante)).thenReturn(restauranteAtualizado);
        when(restauranteAtualizado.getUuid()).thenReturn(uuidRestaurante);
        when(restauranteAtualizado.getNome()).thenReturn("Restaurante Atualizado");
        when(restauranteAtualizado.getEndereco()).thenReturn("Nova Rua, 456");
        when(restauranteAtualizado.getTipoCozinha()).thenReturn("Chinesa");
        when(restauranteAtualizado.getHorarioFuncionamento()).thenReturn(horarioFuncionamento);
        when(horarioFuncionamento.horarioAbertura()).thenReturn(LocalTime.of(12, 0));
        when(horarioFuncionamento.horarioFechamento()).thenReturn(LocalTime.of(23, 30));

        BuscarRestauranteOutput resultado = useCase.execute(input);

        assertNotNull(resultado);
        assertEquals(uuidRestaurante, resultado.uuid());
        assertEquals("Restaurante Atualizado", resultado.nome());
        assertEquals("Nova Rua, 456", resultado.endereco());
        assertEquals("Chinesa", resultado.tipoCozinha());
        assertEquals("12:00", resultado.horarioAbertura());
        assertEquals("23:30", resultado.horarioFechamento());

        verify(restaurante).setNome("Restaurante Atualizado");
        verify(restaurante).setEndereco("Nova Rua, 456");
        verify(restaurante).setTipoCozinha("Chinesa");
        verify(restaurante).setHorarioFuncionamento("12:00", "23:30");
        verify(restauranteInterface).atualizarRestaurante(restaurante);
    }
}

package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.HorarioFuncionamento;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteJaExisteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.restaurante.CriarRestauranteInput;
import br.com.fiap.techchallenge2.domain.output.restaurante.CriarRestauranteOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CriarRestauranteUseCaseTest {

    @Mock
    private RestauranteInterface restauranteInterface;
    @Mock
    private UsuarioInterface usuarioInterface;
    @Mock
    private CardapioInterface cardapioInterface;

    private CriarRestauranteUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new CriarRestauranteUseCase(restauranteInterface, usuarioInterface, cardapioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteJaExiste() {
        CriarRestauranteInput input = mock(CriarRestauranteInput.class);
        when(input.nome()).thenReturn("Restaurante Teste");
        when(restauranteInterface.buscarRestaurantePorNome("Restaurante Teste")).thenReturn(mock(Restaurante.class));

        assertThrows(RestauranteJaExisteException.class, () -> useCase.execute(input));
        verify(restauranteInterface, never()).criarRestaurante(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioDonoNaoExiste() {
        CriarRestauranteInput input = mock(CriarRestauranteInput.class);
        UUID uuidDono = UUID.randomUUID();
        when(input.nome()).thenReturn("Restaurante Teste");
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(restauranteInterface.buscarRestaurantePorNome("Restaurante Teste")).thenReturn(null);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(null);

        assertThrows(UsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(restauranteInterface, never()).criarRestaurante(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForDonoRestaurante() {
        CriarRestauranteInput input = mock(CriarRestauranteInput.class);
        UUID uuidDono = UUID.randomUUID();
        Usuario usuario = mock(Usuario.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        when(input.nome()).thenReturn("Restaurante Teste");
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(restauranteInterface.buscarRestaurantePorNome("Restaurante Teste")).thenReturn(null);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(usuario);
        when(usuario.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn("Cliente");

        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(restauranteInterface, never()).criarRestaurante(any());
    }

    // @Test
    void deveCriarRestauranteComSucesso() {
        CriarRestauranteInput input = mock(CriarRestauranteInput.class);
        UUID uuidDono = UUID.randomUUID();
        UUID uuidRestaurante = UUID.randomUUID();

        Usuario usuario = mock(Usuario.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        Restaurante restauranteCriado = mock(Restaurante.class);
        HorarioFuncionamento horarioFuncionamento = mock(HorarioFuncionamento.class);

        when(input.nome()).thenReturn("Restaurante Teste");
        when(input.endereco()).thenReturn("Rua Teste, 123");
        when(input.tipoCozinha()).thenReturn("Italiana");
        when(input.horarioAbertura()).thenReturn("08:00");
        when(input.horarioFechamento()).thenReturn("22:00");
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);

        when(restauranteInterface.buscarRestaurantePorNome("Restaurante Teste")).thenReturn(null);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(usuario);
        when(usuario.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn("DonoRestaurante");

        when(usuario.getUuid()).thenReturn(uuidDono);
        when(usuario.getNome()).thenReturn("Dono Teste");
        when(usuario.getCpf()).thenReturn("12345678900");
        when(usuario.getEmail()).thenReturn("dono@teste.com");
        when(usuario.getTelefone()).thenReturn("11999999999");
        when(usuario.getEndereco()).thenReturn("Rua Dono, 456");

        when(restauranteInterface.criarRestaurante(any(Restaurante.class))).thenReturn(restauranteCriado);
        when(restauranteCriado.getUuid()).thenReturn(uuidRestaurante);
        when(restauranteCriado.getNome()).thenReturn("Restaurante Teste");
        when(restauranteCriado.getEndereco()).thenReturn("Rua Teste, 123");
        when(restauranteCriado.getTipoCozinha()).thenReturn("Italiana");
        when(restauranteCriado.getHorarioFuncionamento()).thenReturn(horarioFuncionamento);
        when(horarioFuncionamento.horarioAbertura()).thenReturn(LocalTime.of(8, 0));
        when(horarioFuncionamento.horarioFechamento()).thenReturn(LocalTime.of(22, 0));

        CriarRestauranteOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(uuidRestaurante, output.uuid());
        assertEquals("Restaurante Teste", output.nome());
        assertEquals("Rua Teste, 123", output.endereco());
        assertEquals("Italiana", output.tipoCozinha());
        assertEquals("08:00", output.horarioAbertura());
        assertEquals("22:00", output.horarioFechamento());
        assertNotNull(output.donoRestaurante());
        assertEquals(uuidDono, output.donoRestaurante().uuid());
        assertEquals("Dono Teste", output.donoRestaurante().nome());
        assertEquals("DonoRestaurante", output.donoRestaurante().tipoUsuario());

        verify(restauranteInterface).criarRestaurante(any(Restaurante.class));
    }
}

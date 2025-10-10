package br.com.fiap.techchallenge2.domain.usecase.restaurante;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.restaurante.DeletarRestauranteInput;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarRestauranteUseCaseTest {
    @Mock
    private RestauranteInterface restauranteInterface;
    @Mock
    private UsuarioInterface usuarioInterface;
    @Mock
    private CardapioInterface cardapioInterface;
    @Mock
    private ItemCardapioInterface itemCardapioInterface;
    private DeletarRestauranteUseCase useCase;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DeletarRestauranteUseCase(restauranteInterface, usuarioInterface, cardapioInterface, itemCardapioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveDeletarRestauranteComSucesso() {
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidDono = UUID.randomUUID();
        DeletarRestauranteInput input = mock(DeletarRestauranteInput.class);
        Usuario dono = mock(Usuario.class);
        Restaurante restaurante = mock(Restaurante.class);
        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(dono);
        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(restaurante);
        when(restaurante.getDonoRestaurante()).thenReturn(dono);
        when(dono.getUuid()).thenReturn(uuidDono);
        assertDoesNotThrow(() -> useCase.execute(input));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistente() {
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidDono = UUID.randomUUID();
        DeletarRestauranteInput input = mock(DeletarRestauranteInput.class);
        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(null);
        assertThrows(UsuarioInexistenteException.class, () -> useCase.execute(input));
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoExistente() {
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidDono = UUID.randomUUID();
        DeletarRestauranteInput input = mock(DeletarRestauranteInput.class);
        Usuario dono = mock(Usuario.class);
        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(dono);
        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(null);
        assertThrows(RestauranteInexistenteException.class, () -> useCase.execute(input));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEDonoDoRestaurante() {
        UUID uuidRestaurante = UUID.randomUUID();
        UUID uuidDono = UUID.randomUUID();
        UUID uuidOutroDono = UUID.randomUUID();
        DeletarRestauranteInput input = mock(DeletarRestauranteInput.class);
        Usuario dono = mock(Usuario.class);
        Usuario outroDono = mock(Usuario.class);
        Restaurante restaurante = mock(Restaurante.class);
        when(input.uuidRestaurante()).thenReturn(uuidRestaurante);
        when(input.uuidDonoRestaurante()).thenReturn(uuidDono);
        when(usuarioInterface.buscarUsuarioPorUuid(uuidDono)).thenReturn(dono);
        when(restauranteInterface.buscarRestaurantePorUuid(uuidRestaurante)).thenReturn(restaurante);
        when(restaurante.getDonoRestaurante()).thenReturn(outroDono);
        when(outroDono.getUuid()).thenReturn(uuidOutroDono);
        assertThrows(RestauranteInexistenteException.class, () -> useCase.execute(input));
    }
}

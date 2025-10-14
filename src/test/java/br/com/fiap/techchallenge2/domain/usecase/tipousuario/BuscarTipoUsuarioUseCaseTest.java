package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.BuscarTipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioInterface tipoUsuarioInterface;

    private BuscarTipoUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarTipoUsuarioUseCase(tipoUsuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioLogadoNaoForAdmin() {
        BuscarTipoUsuarioInput input = new BuscarTipoUsuarioInput(UUID.randomUUID(), "DonoRestaurante");
        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).buscarTipoUsuarioPorUuid(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExistente() {
        UUID uuid = UUID.randomUUID();
        BuscarTipoUsuarioInput input = new BuscarTipoUsuarioInput(uuid, "Admin");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorUuid(uuid)).thenReturn(null);
        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface).buscarTipoUsuarioPorUuid(uuid);
    }

    @Test
    void deveRetornarTipoUsuarioOutputQuandoExistenteEAdmin() {
        UUID uuid = UUID.randomUUID();
        BuscarTipoUsuarioInput input = new BuscarTipoUsuarioInput(uuid, "Admin");
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        when(tipoUsuario.getId()).thenReturn(uuid);
        when(tipoUsuario.getNome()).thenReturn("Cliente");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorUuid(uuid)).thenReturn(tipoUsuario);

        TipoUsuarioOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(uuid, output.getId());
        assertEquals("Cliente", output.getNome());
        verify(tipoUsuarioInterface).buscarTipoUsuarioPorUuid(uuid);
    }
}

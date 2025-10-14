package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CriarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioInterface tipoUsuarioInterface;

    private CriarTipoUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new CriarTipoUsuarioUseCase(tipoUsuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioLogadoNaoForAdmin() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "DonoRestaurante");
        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).criarTipoUsuario(any());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioJaExiste() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "Admin");
        TipoUsuario tipoUsuarioExistente = mock(TipoUsuario.class);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(tipoUsuarioExistente);
        assertThrows(TipoUsuarioJaExisteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).criarTipoUsuario(any());
    }

    @Test
    void deveCriarTipoUsuarioQuandoNaoExisteEAdmin() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "Admin");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(null);
        TipoUsuario tipoUsuarioCriado = mock(TipoUsuario.class);
        when(tipoUsuarioInterface.criarTipoUsuario(any())).thenReturn(tipoUsuarioCriado);
        when(tipoUsuarioCriado.getId()).thenReturn(java.util.UUID.randomUUID());
        when(tipoUsuarioCriado.getNome()).thenReturn("Cliente");

        TipoUsuarioOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals("Cliente", output.getNome());
        assertNotNull(output.getId());
        verify(tipoUsuarioInterface).criarTipoUsuario(any());
    }
}

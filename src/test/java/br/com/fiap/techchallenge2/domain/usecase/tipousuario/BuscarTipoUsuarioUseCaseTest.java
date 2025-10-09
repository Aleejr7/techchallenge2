package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
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
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "DonoRestaurante");
        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).buscarTipoUsuarioPorNome(anyString());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExistente() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "Admin");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(null);
        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface).buscarTipoUsuarioPorNome("Cliente");
    }

    @Test
    void deveRetornarTipoUsuarioOutputQuandoExistenteEAdmin() {
        UUID id = UUID.randomUUID();
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "Admin");
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        when(tipoUsuario.getId()).thenReturn(id);
        when(tipoUsuario.getNome()).thenReturn("Cliente");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(tipoUsuario);

        TipoUsuarioOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(id, output.uuid());
        assertEquals("Cliente", output.nome());
        verify(tipoUsuarioInterface).buscarTipoUsuarioPorNome("Cliente");
    }
}


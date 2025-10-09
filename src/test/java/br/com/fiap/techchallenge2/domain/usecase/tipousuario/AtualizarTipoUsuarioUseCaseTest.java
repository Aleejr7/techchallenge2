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

public class AtualizarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioInterface tipoUsuarioInterface;

    private AtualizarTipoUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AtualizarTipoUsuarioUseCase(tipoUsuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioLogadoNaoForAdmin() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "DonoRestaurante");
        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).atualizarTipoUsuario(any());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExistente() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "Admin");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(null);
        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).atualizarTipoUsuario(any());
    }

    @Test
    void deveAtualizarTipoUsuarioQuandoExistenteEAdmin() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoUsuarioExistente = mock(TipoUsuario.class);
        when(tipoUsuarioExistente.getId()).thenReturn(id);
        when(tipoUsuarioExistente.getNome()).thenReturn("Cliente");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(tipoUsuarioExistente);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Gerente")).thenReturn(tipoUsuarioExistente);

        TipoUsuario tipoUsuarioAtualizado = mock(TipoUsuario.class);
        when(tipoUsuarioAtualizado.getId()).thenReturn(id);
        when(tipoUsuarioAtualizado.getNome()).thenReturn("Gerente");
        when(tipoUsuarioInterface.atualizarTipoUsuario(any())).thenReturn(tipoUsuarioAtualizado);

        TipoUsuarioOutput output = useCase.execute(new TipoUsuarioInput("Gerente", "Admin"));

        assertNotNull(output);
        assertEquals(id, output.uuid());
        assertEquals("Gerente", output.nome());
        verify(tipoUsuarioInterface).atualizarTipoUsuario(any());
    }
}

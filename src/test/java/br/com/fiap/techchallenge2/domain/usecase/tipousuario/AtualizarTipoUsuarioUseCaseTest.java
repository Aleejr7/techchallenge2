package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.AtualizarTipoUsuarioInput;
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
        AtualizarTipoUsuarioInput input = new AtualizarTipoUsuarioInput(UUID.randomUUID(), "Cliente", "DonoRestaurante");
        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).atualizarTipoUsuario(any());
    }

    @Test
    void deveLancarExcecaoQuandoJaExisteTipoUsuarioComNovoNome() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoUsuarioComNovoNome = mock(TipoUsuario.class);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Gerente")).thenReturn(tipoUsuarioComNovoNome);
        AtualizarTipoUsuarioInput input = new AtualizarTipoUsuarioInput(id, "Gerente", "Admin");
        assertThrows(TipoUsuarioJaExisteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).atualizarTipoUsuario(any());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExistente() {
        UUID id = UUID.randomUUID();
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(null);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorUuid(id)).thenReturn(null);
        AtualizarTipoUsuarioInput input = new AtualizarTipoUsuarioInput(id, "Cliente", "Admin");
        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).atualizarTipoUsuario(any());
    }

    @Test
    void deveAtualizarTipoUsuarioQuandoExistenteEAdmin() {
        UUID id = UUID.randomUUID();
        // Não existe tipo usuário com o novo nome
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Supervisor")).thenReturn(null);
        // Existe tipo usuário com o UUID informado
        TipoUsuario tipoUsuarioExistente = mock(TipoUsuario.class);
        when(tipoUsuarioExistente.getId()).thenReturn(id);
        when(tipoUsuarioExistente.getNome()).thenReturn("Cliente");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorUuid(id)).thenReturn(tipoUsuarioExistente);
        // Após atualização
        TipoUsuario tipoUsuarioAtualizado = mock(TipoUsuario.class);
        when(tipoUsuarioAtualizado.getId()).thenReturn(id);
        when(tipoUsuarioAtualizado.getNome()).thenReturn("Supervisor");
        when(tipoUsuarioInterface.atualizarTipoUsuario(any())).thenReturn(tipoUsuarioAtualizado);

        AtualizarTipoUsuarioInput input = new AtualizarTipoUsuarioInput(id, "Supervisor", "Admin");
        TipoUsuarioOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(id, output.uuid());
        assertEquals("Supervisor", output.nome());
        verify(tipoUsuarioInterface).atualizarTipoUsuario(any());
    }
}

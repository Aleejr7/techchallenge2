package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.DeletarTipoUsuarioInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeletarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioInterface tipoUsuarioInterface;

    private DeletarTipoUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DeletarTipoUsuarioUseCase(tipoUsuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioLogadoNaoForAdmin() {
        DeletarTipoUsuarioInput input = new DeletarTipoUsuarioInput(UUID.randomUUID(), "Cliente");
        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).deletarTipoUsuarioPorUuid(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExistente() {
        UUID uuid = UUID.randomUUID();
        DeletarTipoUsuarioInput input = new DeletarTipoUsuarioInput(uuid, "Admin");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorUuid(uuid)).thenReturn(null);
        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).deletarTipoUsuarioPorUuid(any(UUID.class));
    }

    @Test
    void deveDeletarTipoUsuarioQuandoExistenteEAdmin() {
        UUID uuid = UUID.randomUUID();
        DeletarTipoUsuarioInput input = new DeletarTipoUsuarioInput(uuid, "Admin");
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorUuid(uuid)).thenReturn(tipoUsuario);
        when(tipoUsuario.getId()).thenReturn(uuid);
        useCase.execute(input);
        verify(tipoUsuarioInterface).deletarTipoUsuarioPorUuid(uuid);
    }
}

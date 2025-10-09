package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "DonoRestaurante");
        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).deletarTipoUsuarioPorNome(anyString());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExistente() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "Admin");
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(null);
        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(tipoUsuarioInterface, never()).deletarTipoUsuarioPorNome("Cliente");
    }

    @Test
    void deveDeletarTipoUsuarioQuandoExistenteEAdmin() {
        TipoUsuarioInput input = new TipoUsuarioInput("Cliente", "Admin");
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("Cliente")).thenReturn(tipoUsuario);
        useCase.execute(input);
        verify(tipoUsuarioInterface).deletarTipoUsuarioPorNome("Cliente");
    }
}


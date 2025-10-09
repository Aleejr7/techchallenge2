package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeletarUsuarioUseCaseTest
{

    @Mock
    private UsuarioInterface usuarioInterface;

    private DeletarUsuarioUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DeletarUsuarioUseCase( usuarioInterface );
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveDeletarUsuarioQuandoExistente() {
        UUID uuid = UUID.randomUUID();
        Usuario usuario = mock(Usuario.class);
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(usuario);

        useCase.execute(uuid);

        verify(usuarioInterface).deletarUsuario(uuid);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistente() {
        UUID uuid = UUID.randomUUID();
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(null);

        assertThrows(UsuarioInexistenteException.class, () -> useCase.execute(uuid));

        verify(usuarioInterface, never()).deletarUsuario(uuid);
    }

}

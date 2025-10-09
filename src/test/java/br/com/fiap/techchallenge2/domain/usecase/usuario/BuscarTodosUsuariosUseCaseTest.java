package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarTodosUsuariosUseCaseTest {
    @Mock
    private UsuarioInterface usuarioInterface;
    private BuscarTodosUsuariosUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarTodosUsuariosUseCase(usuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarListaDeUsuariosQuandoAdmin() {
        Usuario usuario1 = mock(Usuario.class);
        Usuario usuario2 = mock(Usuario.class);
        TipoUsuario tipoUsuario1 = mock(TipoUsuario.class);
        TipoUsuario tipoUsuario2 = mock(TipoUsuario.class);
        when(usuario1.getUuid()).thenReturn(java.util.UUID.randomUUID());
        when(usuario1.getNome()).thenReturn("João Silva");
        when(usuario1.getCpf()).thenReturn("12345678901");
        when(usuario1.getEmail()).thenReturn("joao@email.com");
        when(usuario1.getTelefone()).thenReturn("11999999999");
        when(usuario1.getEndereco()).thenReturn("Rua A, 123");
        when(usuario1.getTipoUsuario()).thenReturn(tipoUsuario1);
        when(tipoUsuario1.getNome()).thenReturn("Cliente");

        when(usuario2.getUuid()).thenReturn(java.util.UUID.randomUUID());
        when(usuario2.getNome()).thenReturn("Maria Souza");
        when(usuario2.getCpf()).thenReturn("98765432100");
        when(usuario2.getEmail()).thenReturn("maria@email.com");
        when(usuario2.getTelefone()).thenReturn("11988888888");
        when(usuario2.getEndereco()).thenReturn("Rua B, 456");
        when(usuario2.getTipoUsuario()).thenReturn(tipoUsuario2);
        when(tipoUsuario2.getNome()).thenReturn("Admin");

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioInterface.buscarTodosUsuarios()).thenReturn(usuarios);

        List<UsuarioOutput> outputList = useCase.execute("Admin");

        assertNotNull(outputList);
        assertEquals(2, outputList.size());
        assertEquals("João Silva", outputList.get(0).nome());
        assertEquals("Maria Souza", outputList.get(1).nome());
        verify(usuarioInterface).buscarTodosUsuarios();
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForAdmin() {
        assertThrows(AcessoNegadoException.class, () -> useCase.execute("Cliente"));
        verify(usuarioInterface, never()).buscarTodosUsuarios();
    }
}


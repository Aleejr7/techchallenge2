package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarUsuarioUseCaseTest {
    @Mock
    private UsuarioInterface usuarioInterface;
    private BuscarUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarUsuarioUseCase(usuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarUsuarioQuandoExistente() {
        UUID uuid = UUID.randomUUID();
        Usuario usuario = mock(Usuario.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(usuario);
        when(usuario.getUuid()).thenReturn(uuid);
        when(usuario.getNome()).thenReturn("Nome Teste");
        when(usuario.getCpf()).thenReturn("12345678901");
        when(usuario.getEmail()).thenReturn("teste@teste.com");
        when(usuario.getTelefone()).thenReturn("11999999999");
        when(usuario.getEndereco()).thenReturn("Rua Teste, 123");
        when(usuario.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn("CLIENTE");

        UsuarioOutput output = useCase.execute(uuid);

        assertNotNull(output);
        assertEquals(uuid, output.uuid());
        assertEquals("Nome Teste", output.nome());
        assertEquals("12345678901", output.cpf());
        assertEquals("teste@teste.com", output.email());
        assertEquals("11999999999", output.telefone());
        assertEquals("Rua Teste, 123", output.endereco());
        assertEquals("CLIENTE", output.tipoUsuario());
        verify(usuarioInterface).buscarUsuarioPorUuid(uuid);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistente() {
        UUID uuid = UUID.randomUUID();
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(null);

        assertThrows(UsuarioInexistenteException.class, () -> useCase.execute(uuid));
        verify(usuarioInterface).buscarUsuarioPorUuid(uuid);
    }
}

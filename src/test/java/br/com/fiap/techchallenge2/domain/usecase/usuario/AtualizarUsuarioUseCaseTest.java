package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.AtualizarUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AtualizarUsuarioUseCaseTest {
    @Mock
    private UsuarioInterface usuarioInterface;
    private AtualizarUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AtualizarUsuarioUseCase(usuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveAtualizarTelefoneEEnderecoQuandoUsuarioExistente() {
        UUID uuid = UUID.randomUUID();
        AtualizarUsuarioInput input = mock(AtualizarUsuarioInput.class);
        Usuario usuarioExistente = mock(Usuario.class);
        Usuario usuarioAtualizado = mock(Usuario.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);

        when(input.uuid()).thenReturn(uuid);
        when(input.telefone()).thenReturn("11999999999");
        when(input.endereco()).thenReturn("Rua Nova, 456");
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(usuarioExistente);
        when(usuarioInterface.atualizarUsuario(usuarioExistente)).thenReturn(usuarioAtualizado);
        when(usuarioAtualizado.getUuid()).thenReturn(uuid);
        when(usuarioAtualizado.getNome()).thenReturn("Nome Teste");
        when(usuarioAtualizado.getCpf()).thenReturn("12345678900");
        when(usuarioAtualizado.getEmail()).thenReturn("teste@email.com");
        when(usuarioAtualizado.getTelefone()).thenReturn("11999999999");
        when(usuarioAtualizado.getEndereco()).thenReturn("Rua Nova, 456");
        when(usuarioAtualizado.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn("CLIENTE");

        UsuarioOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(uuid, output.uuid());
        assertEquals("Nome Teste", output.nome());
        assertEquals("12345678900", output.cpf());
        assertEquals("teste@email.com", output.email());
        assertEquals("11999999999", output.telefone());
        assertEquals("Rua Nova, 456", output.endereco());
        assertEquals("CLIENTE", output.tipoUsuario());
        verify(usuarioInterface).buscarUsuarioPorUuid(uuid);
        verify(usuarioExistente).setTelefone("11999999999");
        verify(usuarioExistente).setEndereco("Rua Nova, 456");
        verify(usuarioInterface).atualizarUsuario(usuarioExistente);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistente() {
        UUID uuid = UUID.randomUUID();
        AtualizarUsuarioInput input = mock(AtualizarUsuarioInput.class);
        when(input.uuid()).thenReturn(uuid);
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(null);

        assertThrows(UsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(usuarioInterface).buscarUsuarioPorUuid(uuid);
        verify(usuarioInterface, never()).atualizarUsuario(any(Usuario.class));
    }
}

package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.AlterarTipoDoUsuarioInput;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlterarTipoDoUsuarioUseCaseTest {
    @Mock
    private TipoUsuarioInterface tipoUsuarioInterface;
    @Mock
    private UsuarioInterface usuarioInterface;
    private AlterarTipoDoUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AlterarTipoDoUsuarioUseCase(tipoUsuarioInterface, usuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveAlterarTipoUsuarioQuandoAdminETudoValido() {
        UUID uuid = UUID.randomUUID();
        AlterarTipoDoUsuarioInput input = mock(AlterarTipoDoUsuarioInput.class);
        TipoUsuarioInput tipoInput = mock(TipoUsuarioInput.class);
        Usuario usuarioExistente = mock(Usuario.class);
        TipoUsuario tipoUsuarioNovo = mock(TipoUsuario.class);
        Usuario usuarioAtualizado = mock(Usuario.class);

        when(input.tipoUsuarioLogado()).thenReturn("Admin");
        when(input.uuidUsuario()).thenReturn(uuid);
        when(input.tipoUsuarioInput()).thenReturn(tipoInput);
        when(tipoInput.nome()).thenReturn("CLIENTE");
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(usuarioExistente);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("CLIENTE")).thenReturn(tipoUsuarioNovo);
        when(usuarioInterface.atualizarUsuario(usuarioExistente)).thenReturn(usuarioAtualizado);
        when(usuarioAtualizado.getUuid()).thenReturn(uuid);
        when(usuarioAtualizado.getNome()).thenReturn("Nome Teste");
        when(usuarioAtualizado.getCpf()).thenReturn("12345678900");
        when(usuarioAtualizado.getEmail()).thenReturn("teste@email.com");
        when(usuarioAtualizado.getTelefone()).thenReturn("11999999999");
        when(usuarioAtualizado.getEndereco()).thenReturn("Rua Nova, 456");
        when(usuarioAtualizado.getTipoUsuario()).thenReturn(tipoUsuarioNovo);
        when(tipoUsuarioNovo.getNome()).thenReturn("CLIENTE");

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
        verify(tipoUsuarioInterface).buscarTipoUsuarioPorNome("CLIENTE");
        verify(usuarioExistente).setTipoUsuario(tipoUsuarioNovo);
        verify(usuarioInterface).atualizarUsuario(usuarioExistente);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioLogadoNaoForAdmin() {
        AlterarTipoDoUsuarioInput input = mock(AlterarTipoDoUsuarioInput.class);
        when(input.tipoUsuarioLogado()).thenReturn("Cliente");

        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(usuarioInterface, never()).buscarUsuarioPorUuid(any());
        verify(tipoUsuarioInterface, never()).buscarTipoUsuarioPorNome(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistente() {
        UUID uuid = UUID.randomUUID();
        AlterarTipoDoUsuarioInput input = mock(AlterarTipoDoUsuarioInput.class);
        when(input.tipoUsuarioLogado()).thenReturn("Admin");
        when(input.uuidUsuario()).thenReturn(uuid);
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(null);

        assertThrows(AcessoNegadoException.class, () -> useCase.execute(input));
        verify(usuarioInterface).buscarUsuarioPorUuid(uuid);
        verify(tipoUsuarioInterface, never()).buscarTipoUsuarioPorNome(any());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExistente() {
        UUID uuid = UUID.randomUUID();
        AlterarTipoDoUsuarioInput input = mock(AlterarTipoDoUsuarioInput.class);
        TipoUsuarioInput tipoInput = mock(TipoUsuarioInput.class);
        Usuario usuarioExistente = mock(Usuario.class);
        when(input.tipoUsuarioLogado()).thenReturn("Admin");
        when(input.uuidUsuario()).thenReturn(uuid);
        when(input.tipoUsuarioInput()).thenReturn(tipoInput);
        when(tipoInput.nome()).thenReturn("CLIENTE");
        when(usuarioInterface.buscarUsuarioPorUuid(uuid)).thenReturn(usuarioExistente);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome("CLIENTE")).thenReturn(null);

        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(usuarioInterface).buscarUsuarioPorUuid(uuid);
        verify(tipoUsuarioInterface).buscarTipoUsuarioPorNome("CLIENTE");
        verify(usuarioExistente, never()).setTipoUsuario(any());
        verify(usuarioInterface, never()).atualizarUsuario(any());
    }
}


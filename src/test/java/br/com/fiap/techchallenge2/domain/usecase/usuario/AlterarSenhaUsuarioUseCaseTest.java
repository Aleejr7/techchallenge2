package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.usuario.SenhaErradaException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.AlterarSenhaDoUsuarioInput;
import br.com.fiap.techchallenge2.domain.utils.EncriptadorSenha;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlterarSenhaUsuarioUseCaseTest {
    @Mock
    private UsuarioInterface usuarioInterface;
    private AlterarSenhaUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AlterarSenhaUsuarioUseCase(usuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        String email = "teste@email.com";
        String senhaAntiga = "senhaAntiga";
        String senhaNova = "senhaNova";
        String senhaNovaConfirmacao = "senhaNova";
        Usuario usuario = mock(Usuario.class);
        AlterarSenhaDoUsuarioInput input = mock(AlterarSenhaDoUsuarioInput.class);

        when(input.email()).thenReturn(email);
        when(input.senhaAntiga()).thenReturn(senhaAntiga);
        when(input.senhaNova()).thenReturn(senhaNova);
        when(input.confirmarSenhaNova()).thenReturn(senhaNovaConfirmacao);
        when(usuarioInterface.buscarUsuarioPorEmail(email)).thenReturn(usuario);
        when(usuario.getSenha()).thenReturn(EncriptadorSenha.encriptar(senhaAntiga));
        when(usuarioInterface.atualizarUsuario(usuario)).thenReturn(usuario);

        useCase.execute(input);

        verify(usuarioInterface).buscarUsuarioPorEmail(email);
        verify(usuario).setSenha(anyString());
        verify(usuarioInterface).atualizarUsuario(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistente() {
        String email = "naoexiste@email.com";
        AlterarSenhaDoUsuarioInput input = mock(AlterarSenhaDoUsuarioInput.class);
        when(input.email()).thenReturn(email);
        when(usuarioInterface.buscarUsuarioPorEmail(email)).thenReturn(null);

        assertThrows(UsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(usuarioInterface).buscarUsuarioPorEmail(email);
        verify(usuarioInterface, never()).atualizarUsuario(any());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAntigaErrada() {
        String email = "teste@email.com";
        String senhaAntiga = "senhaErrada";
        Usuario usuario = mock(Usuario.class);
        AlterarSenhaDoUsuarioInput input = mock(AlterarSenhaDoUsuarioInput.class);

        when(input.email()).thenReturn(email);
        when(input.senhaAntiga()).thenReturn(senhaAntiga);
        when(usuarioInterface.buscarUsuarioPorEmail(email)).thenReturn(usuario);
        when(usuario.getSenha()).thenReturn(EncriptadorSenha.encriptar("senhaCorreta"));

        assertThrows(SenhaErradaException.class, () -> useCase.execute(input));
        verify(usuarioInterface).buscarUsuarioPorEmail(email);
        verify(usuarioInterface, never()).atualizarUsuario(any());
    }

    @Test
    void deveLancarExcecaoQuandoNovaSenhaEConfirmacaoNaoCoincidem() {
        String email = "teste@email.com";
        String senhaAntiga = "senhaAntiga";
        String senhaNova = "senhaNova";
        String senhaNovaConfirmacao = "diferente";
        Usuario usuario = mock(Usuario.class);
        AlterarSenhaDoUsuarioInput input = mock(AlterarSenhaDoUsuarioInput.class);

        when(input.email()).thenReturn(email);
        when(input.senhaAntiga()).thenReturn(senhaAntiga);
        when(input.senhaNova()).thenReturn(senhaNova);
        when(input.confirmarSenhaNova()).thenReturn(senhaNovaConfirmacao);
        when(usuarioInterface.buscarUsuarioPorEmail(email)).thenReturn(usuario);
        when(usuario.getSenha()).thenReturn(EncriptadorSenha.encriptar(senhaAntiga));

        assertThrows(SenhaErradaException.class, () -> useCase.execute(input));
        verify(usuarioInterface).buscarUsuarioPorEmail(email);
        verify(usuarioInterface, never()).atualizarUsuario(any());
    }
}


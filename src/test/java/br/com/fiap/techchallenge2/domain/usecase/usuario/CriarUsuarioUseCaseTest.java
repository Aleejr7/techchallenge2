package br.com.fiap.techchallenge2.domain.usecase.usuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.exception.tipousuario.TipoUsuarioInexistenteException;
import br.com.fiap.techchallenge2.domain.exception.usuario.UsuarioJaExisteException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.domain.input.usuario.CriarUsuarioInput;
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

public class CriarUsuarioUseCaseTest {
    @Mock
    private UsuarioInterface usuarioInterface;
    @Mock
    private TipoUsuarioInterface tipoUsuarioInterface;

    private CriarUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new CriarUsuarioUseCase(usuarioInterface, tipoUsuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarUsuarioQuandoNaoExisteETipoValido() {
        String email = "teste@teste.com";
        String nomeTipo = "CLIENTE";
        CriarUsuarioInput input = mock(CriarUsuarioInput.class);
        TipoUsuarioInput tipoInput = mock(TipoUsuarioInput.class);
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        Usuario usuarioCriado = mock(Usuario.class);

        when(input.email()).thenReturn(email);
        when(input.tipoUsuario()).thenReturn(tipoInput);
        when(tipoInput.nome()).thenReturn(nomeTipo);
        when(usuarioInterface.buscarUsuarioPorEmail(email)).thenReturn(null);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome(nomeTipo)).thenReturn(tipoUsuario);
        when(usuarioInterface.criarUsuario(any(Usuario.class))).thenReturn(usuarioCriado);
        when(usuarioCriado.getEmail()).thenReturn(email);
        when(usuarioCriado.getTipoUsuario()).thenReturn(tipoUsuario);
        when(tipoUsuario.getNome()).thenReturn(nomeTipo);
        when(input.nome()).thenReturn("Nome Teste");
        when(input.cpf()).thenReturn("12345678900");
        when(input.senha()).thenReturn("senha123");
        when(input.telefone()).thenReturn("11999999999");
        when(input.endereco()).thenReturn("Rua Teste, 123");

        UsuarioOutput output = useCase.execute(input);

        assertNotNull(output);
        assertEquals(email, output.email());
        assertEquals(nomeTipo, output.tipoUsuario());
        verify(usuarioInterface).criarUsuario(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioJaExiste() {
        String email = "teste@teste.com";
        CriarUsuarioInput input = mock(CriarUsuarioInput.class);
        Usuario usuarioExistente = mock(Usuario.class);
        when(input.email()).thenReturn(email);
        when(usuarioInterface.buscarUsuarioPorEmail(email)).thenReturn(usuarioExistente);

        assertThrows(UsuarioJaExisteException.class, () -> useCase.execute(input));
        verify(usuarioInterface, never()).criarUsuario(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExiste() {
        String email = "teste@teste.com";
        String nomeTipo = "CLIENTE";
        CriarUsuarioInput input = mock(CriarUsuarioInput.class);
        TipoUsuarioInput tipoInput = mock(TipoUsuarioInput.class);
        when(input.email()).thenReturn(email);
        when(input.tipoUsuario()).thenReturn(tipoInput);
        when(tipoInput.nome()).thenReturn(nomeTipo);
        when(usuarioInterface.buscarUsuarioPorEmail(email)).thenReturn(null);
        when(tipoUsuarioInterface.buscarTipoUsuarioPorNome(nomeTipo)).thenReturn(null);

        assertThrows(TipoUsuarioInexistenteException.class, () -> useCase.execute(input));
        verify(usuarioInterface, never()).criarUsuario(any(Usuario.class));
    }
}

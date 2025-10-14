package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.UsuarioRequest;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioModelRepository usuarioModelRepository;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    private UUID uuid;
    private UsuarioModel usuarioModel;
    private TipoUsuarioModel tipoUsuarioModel;
    private UsuarioRequest usuarioRequest;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
        tipoUsuarioModel = new TipoUsuarioModel(uuid, "Cliente");
        String senhaEncriptada = "$2a$10$abcdefghijklmnopqrstuv.5Wz.g.g.g.g.g.g.g.g.g.g.g.g.g.g.g";
        usuarioModel = new UsuarioModel(uuid, "Jane Doe", "12345678901", "john.doe@example.com", senhaEncriptada, "11987654321", "Rua Teste, 123", tipoUsuarioModel);

        TipoUsuarioInput tipoUsuarioInput = new TipoUsuarioInput("Cliente", "Admin");
        usuarioRequest = new UsuarioRequest("Jane Doe", "98765432109", "jane.doe@example.com", "senhaNova", "11912345678", "Rua Nova, 456", tipoUsuarioInput, "senhaAntiga", "senhaNova");
    }

    @Test
    void deveBuscarTodosUsuariosComSucesso() {
        when(usuarioModelRepository.findAll()).thenReturn(List.of(usuarioModel));

        ResponseEntity<List<UsuarioOutput>> response = usuarioController.buscarTodosUsuario("Admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Jane Doe", response.getBody().get(0).nome());
    }

    @Test
    void deveBuscarUsuarioPorUuidComSucesso() {
        when(usuarioModelRepository.findById(uuid)).thenReturn(Optional.of(usuarioModel));

        ResponseEntity<UsuarioOutput> response = usuarioController.buscarUsuario(uuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(uuid, response.getBody().uuid());
    }

    @Test
    void deveCriarUsuarioComSucesso() {
        // Arrange
        when(usuarioModelRepository.findByEmail(anyString())).thenReturn(null);
        when(tipoUsuarioRepository.findByNome(anyString())).thenReturn(tipoUsuarioModel);
        when(usuarioModelRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        // Act
        ResponseEntity<UsuarioOutput> response = usuarioController.criarUsuario(usuarioRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("john.doe@example.com", response.getBody().email());
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        // Arrange
        when(usuarioModelRepository.findById(uuid)).thenReturn(Optional.of(usuarioModel));
        when(usuarioModelRepository.save(any(UsuarioModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<UsuarioOutput> response = usuarioController.atualizarUsuario(uuid, usuarioRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioRequest.nome(), response.getBody().nome());
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        // Arrange
        when(usuarioModelRepository.findById(uuid)).thenReturn(Optional.of(usuarioModel));
        doNothing().when(usuarioModelRepository).deleteById(uuid);

        // Act
        ResponseEntity<Void> response = usuarioController.deletarUsuario(uuid);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // Verifica se o método deleteById foi chamado exatamente uma vez
        verify(usuarioModelRepository, times(1)).deleteById(uuid);
    }

    @Test
    void deveAlterarTipoUsuarioComSucesso() {
        TipoUsuarioModel adminTipo = new TipoUsuarioModel(uuid, "Admin");
        UsuarioRequest requestComNovoTipo = new UsuarioRequest(null, null, null, null, null, null, new TipoUsuarioInput("Admin", "Admin"), null, null);

        when(usuarioModelRepository.findById(uuid)).thenReturn(Optional.of(usuarioModel));
        when(tipoUsuarioRepository.findByNome("Admin")).thenReturn(adminTipo);
        when(usuarioModelRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        ResponseEntity<UsuarioOutput> response = usuarioController.alterarTipoUsuario(uuid, requestComNovoTipo, "Admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioModelRepository, times(1)).save(any(UsuarioModel.class));
    }
}
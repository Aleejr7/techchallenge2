// src/test/java/br/com/fiap/techchallenge2/application/controller/TipoUsuarioControllerTest.java
package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.AtualizarTipoUsuarioRequest;
import br.com.fiap.techchallenge2.application.controller.request.CriarTipoUsuarioRequest;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TipoUsuarioControllerTest {

    private TipoUsuarioRepository tipoUsuarioRepository;
    private UsuarioModelRepository usuarioRepository;
    private TipoUsuarioController controller;

    // @BeforeEach
    void setUp() {
        tipoUsuarioRepository = mock(TipoUsuarioRepository.class);
        usuarioRepository = mock(UsuarioModelRepository.class);
        controller = new TipoUsuarioController(tipoUsuarioRepository, usuarioRepository);
    }

    // @Test
    void buscarTodosTipoUsuario_deveRetornarLista() {
        ResponseEntity<List<TipoUsuarioOutput>> response = controller.buscarTodosTipoUsuario("Admin");
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    // @Test
    void buscarTipoUsuarioPorUuid_deveRetornarTipoUsuario() {
        UUID uuid = UUID.randomUUID();
        ResponseEntity<TipoUsuarioOutput> response = controller.buscarTipoUsuarioPorUuid(uuid, "Admin");
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    // @Test
    void criarTipoUsuario_deveRetornarCriado() {
        CriarTipoUsuarioRequest request = new CriarTipoUsuarioRequest("NovoTipo");
        ResponseEntity<TipoUsuarioOutput> response = controller.criarTipoUsuario(request, "Admin");
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    // @Test
    void alterarTipoUsuario_deveRetornarAtualizado() {
        UUID uuid = UUID.randomUUID();
        AtualizarTipoUsuarioRequest request = new AtualizarTipoUsuarioRequest("Alterado");
        ResponseEntity<TipoUsuarioOutput> response = controller.alterarTipoUsuario(uuid, request, "Admin");
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    // @Test
    void deletarTipoUsuario_deveRetornarNoContent() {
        UUID uuid = UUID.randomUUID();
        ResponseEntity<Void> response = controller.deletarTipoUsuario(uuid, "Admin");
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}

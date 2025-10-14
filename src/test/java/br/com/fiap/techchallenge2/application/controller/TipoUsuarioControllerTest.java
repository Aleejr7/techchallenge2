package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.AtualizarTipoUsuarioRequest;
import br.com.fiap.techchallenge2.application.controller.request.CriarTipoUsuarioRequest;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Mock
    private UsuarioModelRepository usuarioRepository;

    @InjectMocks
    private TipoUsuarioController tipoUsuarioController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tipoUsuarioController).build();
    }

    @Test
    void deveBuscarTodosOsTiposDeUsuarioComSucesso() throws Exception {
        // Arrange
        TipoUsuarioModel tipoAdmin = new TipoUsuarioModel(UUID.randomUUID(), "Admin");
        when(tipoUsuarioRepository.findAll()).thenReturn(List.of(tipoAdmin));

        // Act & Assert
        mockMvc.perform(get(ApiPrefix.BASE + "/tipo-usuario")
                        .header("TipoUsuarioLogado", "Admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Admin"));
    }

    @Test
    void deveBuscarTipoUsuarioPorUuidComSucesso() throws Exception {
        // Arrange
        UUID uuid = UUID.randomUUID();
        TipoUsuarioModel tipoAdmin = new TipoUsuarioModel(uuid, "Admin");
        when(tipoUsuarioRepository.findById(uuid)).thenReturn(Optional.of(tipoAdmin));

        // Act & Assert
        mockMvc.perform(get(ApiPrefix.BASE + "/tipo-usuario/{uuid}", uuid)
                        .header("TipoUsuarioLogado", "Admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(uuid.toString()))
                .andExpect(jsonPath("$.nome").value("Admin"));
    }

    @Test
    void deveCriarTipoUsuarioComSucesso() throws Exception {
        // Arrange
        CriarTipoUsuarioRequest request = new CriarTipoUsuarioRequest("Comum");
        TipoUsuarioModel tipoSalvo = new TipoUsuarioModel(UUID.randomUUID(), "Comum");

        when(tipoUsuarioRepository.findByNome("Comum")).thenReturn(null); // Simula que o nome não existe
        when(tipoUsuarioRepository.save(any(TipoUsuarioModel.class))).thenReturn(tipoSalvo);

        // Act & Assert
        mockMvc.perform(post(ApiPrefix.BASE + "/tipo-usuario")
                        .header("TipoUsuarioLogado", "Admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(tipoSalvo.getId().toString()))
                .andExpect(jsonPath("$.nome").value("Comum"));
    }

    @Test
    void deveAlterarTipoUsuarioComSucesso() throws Exception {
        // Arrange
        UUID uuid = UUID.randomUUID();
        AtualizarTipoUsuarioRequest request = new AtualizarTipoUsuarioRequest("Convidado");
        TipoUsuarioModel tipoExistente = new TipoUsuarioModel(uuid, "NomeAntigo");
        TipoUsuarioModel tipoAtualizado = new TipoUsuarioModel(uuid, "Convidado");

        when(tipoUsuarioRepository.findById(uuid)).thenReturn(Optional.of(tipoExistente));
        when(tipoUsuarioRepository.save(any(TipoUsuarioModel.class))).thenReturn(tipoAtualizado);

        // Act & Assert
        mockMvc.perform(put(ApiPrefix.BASE + "/tipo-usuario/{uuid}", uuid)
                        .header("TipoUsuarioLogado", "Admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // O controller retorna 201, conforme o código
                .andExpect(jsonPath("$.id").value(uuid.toString()))
                .andExpect(jsonPath("$.nome").value("Convidado"));
    }

    @Test
    void deveDeletarTipoUsuarioComSucesso() throws Exception {

        UUID uuid = UUID.randomUUID();
        TipoUsuarioModel tipoExistente = new TipoUsuarioModel(uuid, "ParaDeletar");

        when(tipoUsuarioRepository.findById(uuid)).thenReturn(Optional.of(tipoExistente));

        mockMvc.perform(delete(ApiPrefix.BASE + "/tipo-usuario/{uuid}", uuid)
                        .header("TipoUsuarioLogado", "Admin"))
                .andExpect(status().isNoContent());

        verify(tipoUsuarioRepository, times(1)).deleteById(uuid);
    }

}
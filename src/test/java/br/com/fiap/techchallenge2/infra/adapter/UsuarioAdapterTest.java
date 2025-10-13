package br.com.fiap.techchallenge2.infra.adapter;


import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UsuarioAdapterTest {
    @Mock
    private UsuarioModelRepository repository;

    private UsuarioAdapterRepository adapter;
    AutoCloseable openMocks;
    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        adapter = new UsuarioAdapterRepository(repository);
    }
    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    public void deveBuscarTodosUsuarios(){
        UsuarioModel usuarioModel = criarUsuario();
        Usuario usuarioEntity = new Usuario(
                usuarioModel.getUuid(),
                usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                new TipoUsuario(usuarioModel.getTipoUsuarioModel().getId(),usuarioModel.getTipoUsuarioModel().getNome())
        );
        List<UsuarioModel> listaUsuarioModel = new ArrayList<>();
        List<Usuario> listaUsuarioEntity = new ArrayList<>();
        listaUsuarioEntity.add(usuarioEntity);
        listaUsuarioModel.add(usuarioModel);
        when(repository.findAll()).thenReturn(listaUsuarioModel);

        var listaUsuarioEncontrada = adapter.buscarTodosUsuarios();

        assertThat(listaUsuarioEncontrada).isEqualTo(listaUsuarioEntity);
    }
    @Test
    public void deveBuscarPorUUID(){
        UsuarioModel usuarioModel = criarUsuario();
        Usuario usuarioEntity = new Usuario(
                usuarioModel.getUuid(),
                usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                new TipoUsuario(usuarioModel.getTipoUsuarioModel().getId(),usuarioModel.getTipoUsuarioModel().getNome())
        );
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(usuarioModel));

        var usuarioEncontrado = adapter.buscarUsuarioPorUuid(usuarioModel.getUuid());

        assertThat(usuarioEncontrado).isNotNull().isEqualTo(usuarioEntity);
    }
    @Test
    public void deveBuscarUsuarioPorEmail(){
        UsuarioModel usuarioModel = criarUsuario();
        Usuario usuarioEntity = new Usuario(
                usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                new TipoUsuario(usuarioModel.getTipoUsuarioModel().getId(),usuarioModel.getTipoUsuarioModel().getNome())
        );
        when(repository.findByEmail(any(String.class))).thenReturn(usuarioModel);

        var usuarioEncontrado = adapter.buscarUsuarioPorEmail(usuarioModel.getEmail());

        assertThat(usuarioEncontrado).isNotNull().isEqualTo(usuarioEntity);
    }
    @Test
    public void deveBuscarTodosUsuariosPorTipoUsuario(){
        UsuarioModel usuarioModel = criarUsuario();
        Usuario usuarioEntity = new Usuario(
                usuarioModel.getUuid(),
                usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                new TipoUsuario(usuarioModel.getTipoUsuarioModel().getId(),usuarioModel.getTipoUsuarioModel().getNome())
        );
        List<UsuarioModel> listaUsuarioModel = new ArrayList<>();
        List<Usuario> listaUsuarioEntity = new ArrayList<>();
        listaUsuarioEntity.add(usuarioEntity);
        listaUsuarioModel.add(usuarioModel);
        when(repository.findAllByTipoUsuarioModelId(any(UUID.class))).thenReturn(listaUsuarioModel);

        var listaUsuarioEncontrada = adapter.buscarUsuariosPorTipo(usuarioEntity.getUuid());

        assertThat(listaUsuarioEncontrada).isEqualTo(listaUsuarioEntity);
        assertThat(listaUsuarioEncontrada.get(0).getTipoUsuario().getId()).isEqualTo(listaUsuarioEntity.get(0).getTipoUsuario().getId());
    }
    @Test
    public void deveCriarUsuario(){
        UsuarioModel usuarioModel = criarUsuario();
        Usuario usuarioEntity = new Usuario(
                usuarioModel.getUuid(),
                usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                new TipoUsuario(usuarioModel.getTipoUsuarioModel().getId(),usuarioModel.getTipoUsuarioModel().getNome())
        );
        when(repository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        var usuarioCriado = adapter.criarUsuario(usuarioEntity);

        assertThat(usuarioCriado).isNotNull().isEqualTo(usuarioEntity);
    }
    @Test
    public void deveEditarUsuario(){
        UsuarioModel usuarioModel = criarUsuario();
        Usuario usuarioEntity = new Usuario(
                usuarioModel.getUuid(),
                usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                new TipoUsuario(usuarioModel.getTipoUsuarioModel().getId(),usuarioModel.getTipoUsuarioModel().getNome())
        );
        when(repository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        var usuarioEditado = adapter.atualizarUsuario(usuarioEntity);

        assertThat(usuarioEditado).isNotNull().isEqualTo(usuarioEntity);
    }

    @Test
    public void deveDeletarUsuario(){
        UsuarioModel usuarioModel = criarUsuario();

        adapter.deletarUsuario(usuarioModel.getUuid());

        verify(repository,times(1)).deleteById(usuarioModel.getUuid());
    }
    private UsuarioModel criarUsuario(){
        UUID uuid = UUID.randomUUID();
        UUID uuidUsuario = UUID.randomUUID();
        TipoUsuarioModel tpUsuario = new TipoUsuarioModel(uuid,"Teste");
        return new UsuarioModel(
                uuidUsuario,
                "nome",
                "21321312345",
                "v@gmail.com",
                "senhaaaa",
                "12345678910",
                "endereco",
                tpUsuario
        );
    }
}

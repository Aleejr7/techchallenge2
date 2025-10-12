package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

public class UsuarioRepositoryTest {

    @Mock
    private UsuarioModelRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    public void deveBuscarTodosUsuarios(){
        UsuarioModel usuarioModel = criarUsuario();
        List<UsuarioModel> listaUsuarioModel = new ArrayList<>();
        listaUsuarioModel.add(usuarioModel);
        when(repository.findAll()).thenReturn(listaUsuarioModel);

        var listaUsuarioTipoUsuarioEncontrada = repository.findAll();

        assertThat(listaUsuarioTipoUsuarioEncontrada).isNotNull();
        Assertions.assertEquals(listaUsuarioTipoUsuarioEncontrada.size(),listaUsuarioModel.size());
        Assertions.assertEquals(listaUsuarioTipoUsuarioEncontrada,listaUsuarioModel);
    }
    @Test
    public void deveBuscarUsuarioPorUuid(){
        UsuarioModel usuarioModel = criarUsuario();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(usuarioModel));

        var usuarioModelEncontrado = repository.findById(usuarioModel.getUuid());

        assertThat(usuarioModelEncontrado).isNotNull();
        verify(repository,times(1)).findById(usuarioModel.getUuid());
    }
    @Test
    public void deveBuscarUsuarioPorEmail(){
        UsuarioModel usuarioModel = criarUsuario();
        when(repository.findByEmail(any(String.class))).thenReturn(usuarioModel);

        var usuarioModelEncontrado = repository.findByEmail(usuarioModel.getEmail());

        assertThat(usuarioModelEncontrado).isNotNull().isEqualTo(usuarioModel);
        verify(repository,times(1)).findByEmail(usuarioModel.getEmail());
    }
    @Test
    public void deveBuscarUsuarioPorTipoUsuario(){
        UsuarioModel usuarioModel = criarUsuario();
        List<UsuarioModel> listaUsuarioModel = new ArrayList<>();
        listaUsuarioModel.add(usuarioModel);
        when(repository.findAllByTipoUsuarioModelId(any(UUID.class))).thenReturn(listaUsuarioModel);

        var listaUsuarioTipoUsuarioEncontrada = repository.findAllByTipoUsuarioModelId(listaUsuarioModel.get(0).getTipoUsuarioModel().getId());

        assertThat(listaUsuarioTipoUsuarioEncontrada).isNotNull();
        Assertions.assertEquals(listaUsuarioTipoUsuarioEncontrada.size(),listaUsuarioModel.size());
        Assertions.assertEquals(listaUsuarioTipoUsuarioEncontrada,listaUsuarioModel);
    }
    @Test
    public void deveCriarUsuario(){
        UsuarioModel usuarioModel = criarUsuario();
        when(repository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        var usuarioModelCriado = repository.save(usuarioModel);

        assertThat(usuarioModelCriado).isNotNull();
        verify(repository,times(1)).save(usuarioModelCriado);
    }
    @Test
    public void deveDeletarUsuario(){
        UsuarioModel usuarioModel = criarUsuario();
        doNothing().when(repository).deleteById(any(UUID.class));

        repository.deleteById(usuarioModel.getTipoUsuarioModel().getId());

        verify(repository,times(1)).deleteById(usuarioModel.getTipoUsuarioModel().getId());
    }
    private UsuarioModel criarUsuario(){
        UUID uuid = UUID.fromString("26b43f40-c14e-447f-9fda-c5f90f8887b2");
        TipoUsuarioModel tpUsuario = new TipoUsuarioModel(uuid,"Teste");
        return new UsuarioModel(
                "nome",
                "213213",
                "v@gmail.com",
                "senha",
                "tele",
                "endereco",
                tpUsuario
        );
    }
}

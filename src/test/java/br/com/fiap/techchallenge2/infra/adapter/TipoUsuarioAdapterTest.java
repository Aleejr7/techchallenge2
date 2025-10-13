package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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

public class TipoUsuarioAdapterTest {

    @Mock
    private TipoUsuarioRepository repository;
    private TipoUsuarioAdapterRepository adapter;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        adapter = new TipoUsuarioAdapterRepository(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    public void deveBuscarTodosTiposDeUsuario(){
        var uuid = UUID.randomUUID();
        var nome = "admin";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,nome);
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(uuid,nome);
        List<TipoUsuarioModel> listaTipoModel = new ArrayList<>();
        listaTipoModel.add(tipoUsuarioModel);
        when(repository.findAll()).thenReturn(listaTipoModel);

        var listaEncontrada = adapter.buscarTodosTiposUsuario();

        assertThat(listaEncontrada).satisfies(tp -> {
            var tpPrimeiro = tp.get(0);
            assertThat(tpPrimeiro.getNome()).isEqualTo(tipoUsuarioEntity.getNome());
        });
    }
    @Test
    public void deveBuscarTipoPorId(){
        var uuid = UUID.randomUUID();
        var nome = "admin";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,nome);
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(uuid,nome);
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(tipoUsuarioModel));

        var tipoEncontrado = adapter.buscarTipoUsuarioPorUuid(tipoUsuarioModel.getId());

        assertThat(tipoEncontrado).isNotNull().satisfies(tp -> {
            assertThat(tp.getNome().equals(tipoUsuarioEntity.getNome()));
        });
    }
    @Test
    public void deveBuscarTipoPorNome(){
        var uuid = UUID.randomUUID();
        var nome = "admin";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,nome);
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(uuid,nome);
        when(repository.findByNome(any(String.class))).thenReturn(tipoUsuarioModel);

        var tipoEncontrado = adapter.buscarTipoUsuarioPorNome(tipoUsuarioModel.getNome());

        assertThat(tipoEncontrado).isNotNull().satisfies(tp -> {
            assertThat(tp.getNome().equals(tipoUsuarioEntity.getNome()));
        });
    }
    @Test
    public void deveCriarTipoUsuario(){
        var uuid = UUID.randomUUID();
        var nome = "admin";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,nome);
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(uuid,nome);
        when(repository.save(any(TipoUsuarioModel.class))).thenReturn(tipoUsuarioModel);

        var tipoCriado = adapter.criarTipoUsuario(tipoUsuarioEntity);

        assertThat(tipoCriado).isNotNull().isEqualTo(tipoUsuarioEntity);
    }
    @Test
    public void deveEditarTipoUsuario(){
        var uuid = UUID.randomUUID();
        var nome = "admin";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,nome);
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(uuid,nome);
        when(repository.save(any(TipoUsuarioModel.class))).thenReturn(tipoUsuarioModel);

        var tipoEditado = adapter.atualizarTipoUsuario(tipoUsuarioEntity);

        assertThat(tipoEditado).isEqualTo(tipoUsuarioEntity);
    }
    @Test
    public void deveDeletarTipoUsuario(){
        var uuid = UUID.randomUUID();
        var nome = "admin";
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,nome);

        adapter.deletarTipoUsuarioPorUuid(tipoUsuarioModel.getId());

        verify(repository,times(1)).deleteById(tipoUsuarioModel.getId());
    }

}

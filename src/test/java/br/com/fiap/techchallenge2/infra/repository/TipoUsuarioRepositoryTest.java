package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TipoUsuarioRepositoryTest {

    @Mock
    private TipoUsuarioRepository repository;

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
    public void deveBuscarPorTodos(){
        UUID uuid = UUID.fromString("26b43f40-c14e-447f-9fda-c5f90f8887b2");
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(
              uuid,
        "Teste");
        List<TipoUsuarioModel> listaTiposUsuarioModel = new ArrayList<>();
        listaTiposUsuarioModel.add(tipoUsuarioModel);
        when(repository.findAll()).thenReturn(listaTiposUsuarioModel);

        var listaEncontrada = repository.findAll();

        assertThat(listaEncontrada).isNotNull();
        Assertions.assertEquals(listaEncontrada.size(),listaTiposUsuarioModel.size());
        Assertions.assertEquals(listaEncontrada,listaTiposUsuarioModel);
    }
    @Test
    public void deveRetornarListaVaziaQuandoNaoHouverTiposDeUsuario() {
        List<TipoUsuarioModel> listaTipoUsuario = new ArrayList<>();
        when(repository.findAll()).thenReturn(listaTipoUsuario);

        var listaEncontrada = repository.findAll();

        assertThat(listaEncontrada).isNotNull();
        assertThat(listaEncontrada).isEqualTo(listaTipoUsuario);
        verify(repository, times(1)).findAll();
    }
    @Test
    public void deveBuscarPorUuidTipoDeUsuario(){
        var uuid = UUID.randomUUID();
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(
                uuid,
                "Teste");
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(tipoUsuarioModel));

        var tipoUsuarioEncontrado = repository.findById(uuid);

        assertThat(tipoUsuarioEncontrado).isNotNull();
        verify(repository,times(1)).findById(uuid);
    }
    @Test
    public void naoDeveBuscarPorUuidDeTipoDeUsuarioInexistente() {
        var uuid = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var tipoUsuarioNaoEncontrado = repository.findById(uuid);

        assertThat(tipoUsuarioNaoEncontrado).isEmpty();
        verify(repository, times(1)).findById(uuid);
    }
    @Test
    public void deveBuscarPorNomeTipoDeUsuario(){
        UUID uuid = UUID.fromString("26b43f40-c14e-447f-9fda-c5f90f8887b2");
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(
                uuid,
                "Teste");
        when(repository.findByNome(any(String.class))).thenReturn(tipoUsuarioModel);

        var tipoUsuarioEncontrado = repository.findByNome(tipoUsuarioModel.getNome());

        assertThat(tipoUsuarioEncontrado).isNotNull().isEqualTo(tipoUsuarioModel);
        verify(repository,times(1)).findByNome(tipoUsuarioModel.getNome());
    }

    @Test
    public void naoDeveBuscarPorNomeDeTipoDeUsuarioInexistente() {
        var nomeInexistente = "Tipo Inexistente";
        when(repository.findByNome(any(String.class))).thenReturn(null);

        var tipoUsuarioNaoEncontrado = repository.findByNome(nomeInexistente);

        assertThat(tipoUsuarioNaoEncontrado).isNull();
        verify(repository, times(1)).findByNome(nomeInexistente);
    }
    @Test
    public void deveSalvarTipoDeUsuario(){
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel();
        tipoUsuarioModel.setNome("Teste");
        when(repository.save(any(TipoUsuarioModel.class))).thenReturn(tipoUsuarioModel);

        var tipoUsuarioModelCriado = repository.save(tipoUsuarioModel);

        assertThat(tipoUsuarioModelCriado).isNotNull();
        verify(repository,times(1)).save(tipoUsuarioModelCriado);
    }
    @Test
    public void deveLancarExcecaoAoSalvarTipoDeUsuarioInvalido() {
        TipoUsuarioModel tipoUsuarioInvalido = new TipoUsuarioModel();
        tipoUsuarioInvalido.setNome("");
        when(repository.save(any(TipoUsuarioModel.class)))
                .thenThrow(new DataIntegrityViolationException("Violação de constraint de nome único"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(tipoUsuarioInvalido);
        });

        verify(repository, times(1)).save(tipoUsuarioInvalido);
    }
    @Test
    public void deveDeletarTipoUsuario(){
        var uuid = UUID.randomUUID();
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(
                uuid,
                "Teste");

        doNothing().when(repository).deleteById(any(UUID.class));

        repository.deleteById(uuid);

        verify(repository,times(1)).deleteById(uuid);
    }
    @Test
    public void deveLancarExcecaoAoDeletarTipoDeUsuarioInexistente() {
        var uuid = UUID.randomUUID();
        doThrow(new EmptyResultDataAccessException(1))
                .when(repository).deleteById(any(UUID.class));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(uuid);
        });

        verify(repository, times(1)).deleteById(uuid);
    }
}
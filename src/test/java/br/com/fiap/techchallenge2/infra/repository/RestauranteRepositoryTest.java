package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestauranteRepositoryTest {

    @Mock
    private RestauranteModelRepository repository;

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
    public void deveRetornarTodosRestaurantes(){
        RestauranteModel restauranteModel = criarRestaurante();
        List<RestauranteModel> listaRestaurantesModels = new ArrayList<>();
        listaRestaurantesModels.add(restauranteModel);
        when(repository.findAll()).thenReturn(listaRestaurantesModels);

        var listaRestuarantesEncontrada = repository.findAll();

        assertThat(listaRestuarantesEncontrada).isNotNull();
        Assertions.assertEquals(listaRestuarantesEncontrada.size(),listaRestaurantesModels.size());
        Assertions.assertEquals(listaRestuarantesEncontrada,listaRestaurantesModels);
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHouverRestaurantes() {
        List<RestauranteModel> listaRestaurantes = new ArrayList();
        when(repository.findAll()).thenReturn(listaRestaurantes);

        var listaRestaurantesNaoEncontrado = repository.findAll();

        assertThat(listaRestaurantesNaoEncontrado).isNotNull();
        assertThat(listaRestaurantesNaoEncontrado).isEqualTo(listaRestaurantes);
        verify(repository, times(1)).findAll();
    }
    @Test
    public void deveRetornarRestaurantePorUuid(){
        RestauranteModel restauranteModel = criarRestaurante();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(restauranteModel));

        var restauranteModelEncontrado = repository.findById(restauranteModel.getUuid());

        assertThat(restauranteModelEncontrado).isNotNull();
        verify(repository,times(1)).findById(restauranteModel.getUuid());
    }
    @Test
    public void naoDeveRetornarRestaurantePorUuid() {
        var uuid = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var restauranteNaoEncontrado = repository.findById(uuid);

        assertThat(restauranteNaoEncontrado).isEmpty();
        verify(repository, times(1)).findById(uuid);
    }

    @Test
    public void deveRetornarRestaurantePorNome(){
        RestauranteModel restauranteModel = criarRestaurante();
        when(repository.findByNome(any(String.class))).thenReturn(restauranteModel);

        var restauranteEncontrado = repository.findByNome(restauranteModel.getNome());

        assertThat(restauranteEncontrado).isNotNull().isEqualTo(restauranteModel);
        verify(repository,times(1)).findByNome(restauranteEncontrado.getNome());
    }
    @Test
    public void naoDeveRetornarRestaurantePorNomeInexistente() {
        var nomeInexistente = "Restaurante Fantasma";
        when(repository.findByNome(any(String.class))).thenReturn(null);

        var restauranteNaoEncontrado = repository.findByNome(nomeInexistente);

        assertThat(restauranteNaoEncontrado).isNull();
        verify(repository, times(1)).findByNome(nomeInexistente);
    }
    @Test
    public void deveCriarRestaurante(){
        RestauranteModel restauranteModel = criarRestaurante();
        when(repository.save(any(RestauranteModel.class))).thenReturn(restauranteModel);

        var restauranteSalvo = repository.save(restauranteModel);

        assertThat(restauranteSalvo).isNotNull();
        verify(repository,times(1)).save(restauranteSalvo);
    }
    @Test
    public void deveLancarExcecaoAoTentarCriarRestauranteInvalido() {
        RestauranteModel restauranteInvalido = criarRestaurante();
        when(repository.save(any(RestauranteModel.class)))
                .thenThrow(new DataIntegrityViolationException("Violação de constraint de dados"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(restauranteInvalido);
        });

        verify(repository, times(1)).save(restauranteInvalido);
    }
    @Test
    public void deveDeletarRestaurantePorId(){
        RestauranteModel restauranteModel = criarRestaurante();
        doNothing().when(repository).deleteById(any(UUID.class));

        repository.deleteById(restauranteModel.getUuid());

        verify(repository,times(1)).deleteById(restauranteModel.getUuid());
    }
    @Test
    public void deveLancarExcecaoAoTentarDeletarRestauranteInexistente() {
        var uuidInexistente = UUID.randomUUID();
        doThrow(new EmptyResultDataAccessException(1))
                .when(repository).deleteById(any(UUID.class));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(uuidInexistente);
        });

        verify(repository, times(1)).deleteById(uuidInexistente);
    }
    private RestauranteModel criarRestaurante(){
        UUID uuid = UUID.fromString("26b43f40-c14e-447f-9fda-c5f90f8887b2");
        TipoUsuarioModel tpUsuario = new TipoUsuarioModel(uuid,"Teste");
        HorarioFuncionamentoModel hora = new HorarioFuncionamentoModel(converterStringParaLocalTime("12:00"),
                converterStringParaLocalTime("22:00"));
        UsuarioModel usuarioModel = new UsuarioModel(
                "nome",
                "213213",
                "v@gmail.com",
                "senha",
                "tele",
                "endereco",
                tpUsuario
        );
        return new RestauranteModel(
                "nomeR",
                "EnderecoR",
                "TipoR",
                hora,
                usuarioModel
        );
    }
    private LocalTime converterStringParaLocalTime( String horario ) {
        String[] partes = horario.split(":");
        int hora = Integer.parseInt(partes[0]);
        int minuto = Integer.parseInt(partes[1]);
        return LocalTime.of(hora, minuto);
    }
}

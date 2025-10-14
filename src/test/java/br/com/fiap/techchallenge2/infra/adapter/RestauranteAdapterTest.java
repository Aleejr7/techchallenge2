package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.infra.model.HorarioFuncionamentoModel;
import br.com.fiap.techchallenge2.infra.model.RestauranteModel;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.RestauranteModelRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RestauranteAdapterTest {

    @Mock
    private RestauranteModelRepository repository;

    @Mock
    private UsuarioModelRepository usuarioRepository;

    private RestauranteAdapterRepository adapter;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        adapter = new RestauranteAdapterRepository(repository, usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void deveCriarRestaurante() {
        UsuarioModel donoModel = criarUsuarioModel();
        Usuario donoEntity = criarUsuarioEntity(donoModel);

        RestauranteModel restauranteModelSalvo = criarRestauranteModel(donoModel);
        Restaurante restauranteEsperado = criarRestauranteEntity(restauranteModelSalvo);

        when(usuarioRepository.findById(any(UUID.class))).thenReturn(Optional.of(donoModel));
        when(repository.save(any(RestauranteModel.class))).thenReturn(restauranteModelSalvo);

        var restauranteCriado = adapter.criarRestaurante(restauranteEsperado);

        assertThat(restauranteCriado).isNotNull();
        assertThat(restauranteCriado.getUuid()).isEqualTo(restauranteEsperado.getUuid());
        assertThat(restauranteCriado.getNome()).isEqualTo(restauranteEsperado.getNome());
        verify(usuarioRepository, times(1)).findById(donoEntity.getUuid());
        verify(repository, times(1)).save(any(RestauranteModel.class));
    }

    @Test
    void deveBuscarRestaurantePorUuid() {
        RestauranteModel restauranteModel = criarRestauranteModel(criarUsuarioModel());
        Restaurante restauranteEsperado = criarRestauranteEntity(restauranteModel);

        when(repository.findById(restauranteModel.getUuid())).thenReturn(Optional.of(restauranteModel));
        var restauranteEncontrado = adapter.buscarRestaurantePorUuid(restauranteModel.getUuid());

        assertThat(restauranteEncontrado).isNotNull().isEqualTo(restauranteEsperado);
    }

    @Test
    void deveRetornarNuloQuandoBuscarRestaurantePorUuidInexistente() {
        UUID idInexistente = UUID.randomUUID();
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        var resultado = adapter.buscarRestaurantePorUuid(idInexistente);

        assertThat(resultado).isNull();
    }

    @Test
    void deveBuscarRestaurantePorNome() {
        RestauranteModel restauranteModel = criarRestauranteModel(criarUsuarioModel());
        Restaurante restauranteEsperado = criarRestauranteEntity(restauranteModel);
        String nome = restauranteModel.getNome();
        when(repository.findByNome(any(String.class))).thenReturn(restauranteModel);

        var restauranteEncontrado = adapter.buscarRestaurantePorNome(nome);

        assertThat(restauranteEncontrado).isNotNull();
        assertThat(restauranteEsperado.getNome()).isEqualTo(restauranteEncontrado.getNome());
    }

    @Test
    void deveBuscarTodosRestaurantes() {
        RestauranteModel restauranteModel = criarRestauranteModel(criarUsuarioModel());
        List<RestauranteModel> listaModel = List.of(restauranteModel);
        List<Restaurante> listaEsperada = List.of(criarRestauranteEntity(restauranteModel));
        when(repository.findAll()).thenReturn(listaModel);

        var listaEncontrada = adapter.buscarTodosRestaurantes();

        assertThat(listaEncontrada.size()).isEqualTo(listaEsperada.size());
        assertThat(listaEncontrada.get(0).getNome()).isEqualTo(listaEsperada.get(0).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverRestaurantes() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        var listaEncontrada = adapter.buscarTodosRestaurantes();

        assertThat(listaEncontrada).isNotNull().isEmpty();
    }

    @Test
    void deveAtualizarRestaurante() {
        UUID restauranteId = UUID.randomUUID();
        UsuarioModel donoModel = criarUsuarioModel();


        RestauranteModel restauranteModelExistente = new RestauranteModel(
                restauranteId,
                "Restaurante Antigo",
                "Rua Velha, 123",
                "Brasileira",
                new HorarioFuncionamentoModel(LocalTime.parse("10:00"), LocalTime.parse("22:00")),
                donoModel,
                null
        );

        String novoNome = "Restaurante Novo Sabor";
        String novoEndereco = "Avenida Principal, 456";
        Restaurante restauranteComNovosDados = criarRestauranteEntity(restauranteModelExistente);
        restauranteComNovosDados.setNome(novoNome);
        restauranteComNovosDados.setEndereco(novoEndereco);
        
        RestauranteModel restauranteModelSalvo = new RestauranteModel(
                restauranteId,
                novoNome,
                novoEndereco,
                restauranteComNovosDados.getTipoCozinha(),
                new HorarioFuncionamentoModel(
                        restauranteComNovosDados.getHorarioFuncionamento().horarioAbertura(),
                        restauranteComNovosDados.getHorarioFuncionamento().horarioFechamento()),
                donoModel,
                restauranteComNovosDados.getCardapioId()
        );

        when(repository.findById(restauranteId)).thenReturn(Optional.of(restauranteModelExistente));
        when(repository.save(any(RestauranteModel.class))).thenReturn(restauranteModelSalvo);

        var restauranteAtualizado = adapter.atualizarRestaurante(restauranteComNovosDados);


        assertThat(restauranteAtualizado).isNotNull();
        assertThat(restauranteAtualizado.getNome()).isEqualTo(novoNome);
        assertThat(restauranteAtualizado.getEndereco()).isEqualTo(novoEndereco);
        assertThat(restauranteAtualizado.getUuid()).isEqualTo(restauranteId);

        verify(repository, times(1)).findById(restauranteId);
        verify(repository, times(1)).save(any(RestauranteModel.class));
    }

    private UsuarioModel criarUsuarioModel() {
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174005");;
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(uuid,"teste");

        return new UsuarioModel(uuid,"Dono Teste",
                "12345678901", "dono@teste.com",
                "senha123", "11999999999", "Rua Teste, 123",tipoUsuarioModel
        );
    }

    private RestauranteModel criarRestauranteModel(UsuarioModel dono) {
        HorarioFuncionamentoModel horarioModel = new HorarioFuncionamentoModel(
                LocalTime.parse("10:00"), LocalTime.parse("22:00")
        );
        return new RestauranteModel(
                "Restaurante Fictício", "Rua da Ficção, 100", "Brasileira",
                horarioModel, dono
        );
    }

    private Usuario criarUsuarioEntity(UsuarioModel usuarioModel) {
        TipoUsuario tipoUsuario = new TipoUsuario(usuarioModel.getUuid(), usuarioModel.getNome());
        return new Usuario(
                usuarioModel.getUuid(),
                usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                tipoUsuario
        );
    }

    private Restaurante criarRestauranteEntity(RestauranteModel restauranteModel) {
        Usuario dono = criarUsuarioEntity(restauranteModel.getDonoRestaurante());
        return new Restaurante(
                restauranteModel.getUuid(),
                restauranteModel.getNome(),
                restauranteModel.getEndereco(),
                restauranteModel.getTipoCozinha(),
                restauranteModel.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteModel.getHorarioFuncionamento().horarioFechamento().toString(),
                dono
        );
    }
}
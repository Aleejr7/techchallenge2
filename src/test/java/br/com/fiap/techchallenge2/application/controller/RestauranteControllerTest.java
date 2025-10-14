package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.RestauranteRequest;
import br.com.fiap.techchallenge2.domain.exception.restaurante.RestauranteInexistenteException;
import br.com.fiap.techchallenge2.domain.output.restaurante.BuscarRestauranteOutput;
import br.com.fiap.techchallenge2.domain.output.restaurante.CriarRestauranteOutput;
import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import br.com.fiap.techchallenge2.infra.model.HorarioFuncionamentoModel;
import br.com.fiap.techchallenge2.infra.model.RestauranteModel;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.RestauranteModelRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteControllerTest {

    @Mock
    private RestauranteModelRepository restauranteRepository;
    @Mock
    private UsuarioModelRepository usuarioRepository;
    @Mock
    private CardapioModelRepository cardapioRepository;
    @Mock
    private ItemCardapioModelRepository itemCardapioRepository;

    private RestauranteController controller;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
        // Instancia o controller manualmente com os repositórios mockados
        controller = new RestauranteController(
                restauranteRepository,
                usuarioRepository,
                cardapioRepository,
                itemCardapioRepository
        );
    }

    @Test
    @DisplayName("Deve buscar todos os restaurantes e retornar com sucesso")
    void deveBuscarTodosRestaurantesComSucesso() {
        // Arrange
        UsuarioModel dono = criarUsuarioModel(UUID.randomUUID(), "Dono");
        RestauranteModel restauranteModel = criarRestauranteModel(UUID.randomUUID(), "Restaurante Teste", dono);
        when(restauranteRepository.findAll()).thenReturn(Collections.singletonList(restauranteModel));

        // Act
        ResponseEntity<List<BuscarRestauranteOutput>> response = controller.buscarTodosRestaurantes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Restaurante Teste", response.getBody().get(0).nome());
        verify(restauranteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar um restaurante por UUID e retornar com sucesso")
    void deveBuscarRestaurantePorUuidComSucesso() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        UsuarioModel dono = criarUsuarioModel(UUID.randomUUID(), "Dono");
        RestauranteModel restauranteModel = criarRestauranteModel(restauranteId, "Restaurante Encontrado", dono);
        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restauranteModel));

        // Act
        ResponseEntity<BuscarRestauranteOutput> response = controller.buscarRestaurante(restauranteId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Restaurante Encontrado", response.getBody().nome());
        assertEquals(restauranteId, response.getBody().uuid());
        verify(restauranteRepository, times(1)).findById(restauranteId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar restaurante com UUID inexistente")
    void deveLancarExcecaoAoBuscarRestauranteInexistente() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestauranteInexistenteException.class, () -> {
            controller.buscarRestaurante(restauranteId);
        });
        verify(restauranteRepository, times(1)).findById(restauranteId);
    }


    @Test
    @DisplayName("Deve criar um novo restaurante e retornar status 201 Created")
    void deveCriarRestauranteComSucesso() {
        // Arrange
        UUID donoId = UUID.randomUUID();
        UUID restauranteIdGerado = UUID.randomUUID();
        RestauranteRequest request = new RestauranteRequest(null, "Novo Restaurante", "Rua Nova, 123", "Brasileira", "10:00", "22:00", donoId);

        UsuarioModel dono = criarUsuarioModel(donoId, "DonoRestaurante");

        CardapioModel cardapioNovo = new CardapioModel();
        setPrivateField(cardapioNovo, "uuid", UUID.randomUUID());
        cardapioNovo.setNome("Cardápio padrão");

        when(restauranteRepository.findByNome(request.nome())).thenReturn(null);
        when(usuarioRepository.findById(donoId)).thenReturn(Optional.of(dono));
        when(cardapioRepository.save(any(CardapioModel.class))).thenReturn(cardapioNovo);

        when(restauranteRepository.save(any(RestauranteModel.class))).thenAnswer(invocation -> {
            RestauranteModel modelQueSeriaSalvo = invocation.getArgument(0);
            if (modelQueSeriaSalvo.getUuid() == null) {
                setPrivateField(modelQueSeriaSalvo, "uuid", restauranteIdGerado);
            }
            return modelQueSeriaSalvo;
        });


        ResponseEntity<CriarRestauranteOutput> response = controller.criarRestaurante(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Novo Restaurante", response.getBody().nome());
        assertEquals(restauranteIdGerado, response.getBody().uuid());

        verify(restauranteRepository, times(1)).findByNome(request.nome());
        verify(usuarioRepository, times(2)).findById(donoId);
        verify(restauranteRepository, times(1)).save(any(RestauranteModel.class));
        verify(cardapioRepository, times(1)).save(any(CardapioModel.class));
    }

    @Test
    @DisplayName("Deve alterar um restaurante existente e retornar status 200 OK")
    void deveAlterarRestauranteComSucesso() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteRequest request = new RestauranteRequest(null, "Nome Alterado", "End Alterado", "Japonesa", "12:00", "23:00", donoId);

        UsuarioModel dono = criarUsuarioModel(donoId, "Dono");
        RestauranteModel restauranteExistente = criarRestauranteModel(restauranteId, "Nome Antigo", dono);

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restauranteExistente));
        when(usuarioRepository.findById(donoId)).thenReturn(Optional.of(dono));
        when(restauranteRepository.save(any(RestauranteModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<CriarRestauranteOutput> response = controller.alterarRestaurante(restauranteId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); // O método do controller retorna .build(), então o corpo é nulo
        verify(restauranteRepository, times(2)).findById(restauranteId);
        verify(usuarioRepository, times(1)).findById(donoId);
        verify(restauranteRepository, times(1)).save(any(RestauranteModel.class));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao tentar alterar com UUID nulo")
    void deveRetornarBadRequestAoAlterarComUuidNulo() {
        // Arrange
        RestauranteRequest request = new RestauranteRequest(null, "Nome", "End", "Tipo", "10:00", "22:00", UUID.randomUUID());

        // Act
        ResponseEntity<CriarRestauranteOutput> response = controller.alterarRestaurante(null, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(restauranteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao tentar alterar com dono nulo")
    void deveRetornarBadRequestAoAlterarComDonoNulo() {
        // Arrange
        RestauranteRequest request = new RestauranteRequest(null, "Nome", "End", "Tipo", "10:00", "22:00", null);

        // Act
        ResponseEntity<CriarRestauranteOutput> response = controller.alterarRestaurante(UUID.randomUUID(), request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(restauranteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar um restaurante e retornar status 204 No Content")
    void deveDeletarRestauranteComSucesso() {
        // Arrange
        UUID restauranteId = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteRequest request = new RestauranteRequest(null, null, null, null, null, null, donoId);

        UsuarioModel dono = criarUsuarioModel(donoId, "Dono");
        RestauranteModel restauranteExistente = criarRestauranteModel(restauranteId, "A ser deletado", dono);
        restauranteExistente.setCardapioId(UUID.randomUUID());

        CardapioModel cardapioExistente = new CardapioModel();

        setPrivateField(cardapioExistente, "uuid", restauranteExistente.getCardapioId());
        setPrivateField(cardapioExistente, "itensCardapio", new ArrayList<>());


        cardapioExistente.setNome("Cardapio");

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restauranteExistente));
        when(usuarioRepository.findById(donoId)).thenReturn(Optional.of(dono));

        when(cardapioRepository.findByUuidRestaurante(restauranteId)).thenReturn(cardapioExistente);

        // Act
        ResponseEntity<Void> response = controller.deletarRestaurante(restauranteId, request);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(restauranteRepository, times(1)).deleteById(restauranteId);
        // Verificando os métodos corretos de deleção
        verify(itemCardapioRepository, times(1)).deleteByCardapioModelUuid(cardapioExistente.getUuid());
        verify(cardapioRepository, times(1)).deleteById(cardapioExistente.getUuid());
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao tentar deletar com UUID nulo")
    void deveRetornarBadRequestAoDeletarComUuidNulo() {
        // Arrange
        RestauranteRequest request = new RestauranteRequest(null, null, null, null, null, null, UUID.randomUUID());

        // Act
        ResponseEntity<Void> response = controller.deletarRestaurante(null, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(restauranteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao tentar deletar com dono nulo")
    void deveRetornarBadRequestAoDeletarComDonoNulo() {
        // Arrange
        RestauranteRequest request = new RestauranteRequest(null, null, null, null, null, null, null);

        // Act
        ResponseEntity<Void> response = controller.deletarRestaurante(UUID.randomUUID(), request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(restauranteRepository, never()).deleteById(any());
    }


    private UsuarioModel criarUsuarioModel(UUID id, String nomeTipoUsuario) {
        TipoUsuarioModel tipo = new TipoUsuarioModel();
        tipo.setNome(nomeTipoUsuario);
        return new UsuarioModel(id, "Nome Usuario", "12345678901", "email@test.com", "senha123", "11999998888", "Rua Teste", tipo);
    }

    private RestauranteModel criarRestauranteModel(UUID id, String nome, UsuarioModel dono) {
        HorarioFuncionamentoModel horario = new HorarioFuncionamentoModel(LocalTime.of(10, 0), LocalTime.of(22, 0));
        return new RestauranteModel(id, nome, "Endereço Teste", "Cozinha Teste", horario, dono, null);
    }



    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // Permite acesso ao atributo privado
            field.set(object, value);   // Atribui o novo valor
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Falha ao setar atributo privado via reflection", e);
        }
    }
}
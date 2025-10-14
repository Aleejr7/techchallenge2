package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.infra.model.*;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CardapioAdapterTest {

    @Mock
    private ItemCardapioModelRepository itemRepository;
    @Mock
    private CardapioModelRepository cardapioRepository;
    private CardapioAdapterRepository adapter;
    AutoCloseable openMocks;
    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        adapter = new CardapioAdapterRepository(cardapioRepository,itemRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void deveCriarCardapio() {
        var uuidRestaurante = UUID.randomUUID();
        HorarioFuncionamentoModel horarioModel = new HorarioFuncionamentoModel(
                LocalTime.parse("10:00"), LocalTime.parse("22:00")
        );
        Usuario usuario = criarUsuario();
        Restaurante restaurante = new Restaurante(uuidRestaurante,
                "nome",
                "endereco",
                "tipo",
                horarioModel.horarioAbertura().toString(),
                horarioModel.horarioFechamento().toString(),
                usuario);
        CardapioModel cardapioModel = new CardapioModel();
        cardapioModel.setNome("Cardapio Teste");
        cardapioModel.setUuidRestaurante(uuidRestaurante);
        Cardapio cardapioEntity = new Cardapio(cardapioModel.getNome(),cardapioModel.getUuidRestaurante());
        when(cardapioRepository.save(any(CardapioModel.class))).thenReturn(cardapioModel);

        var cardapioCriado = adapter.criarCardapio(restaurante,cardapioEntity.getNome());

        assertThat(cardapioCriado).isEqualTo(cardapioEntity);
    }
    private Usuario criarUsuario(){
        UUID uuid = UUID.randomUUID();
        UUID uuidUsuario = UUID.randomUUID();
        TipoUsuario tpUsuario = new TipoUsuario(uuid,"Teste");
        return new Usuario(
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

package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ItemCardapioRepositoryTest {
    @Mock
    private ItemCardapioModelRepository repository;
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
    public void deveRetornarItemPorUUID(){
        ItemCardapioModel itemCardapioModel = criarItemCardapio();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(itemCardapioModel));

        var itemEncontrado = repository.findById(itemCardapioModel.getUuid());

        assertThat(itemEncontrado).isNotNull();
        verify(repository,times(1)).findById(itemCardapioModel.getUuid());
    }
    @Test
    public void naoDeveRetornarItemPorUUIDInexistente() {
        var uuid = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var itemNaoEncontrado = repository.findById(uuid);

        assertThat(itemNaoEncontrado).isEmpty();
        verify(repository, times(1)).findById(uuid);
    }
    @Test
    public void deveRetornarListaItensPorCardapio(){
        UUID uuid = UUID.fromString("26b43f40-c14e-447f-9fda-c5f90f8887b2");
        ItemCardapioModel itemCardapioModel = criarItemCardapio();
        List<ItemCardapioModel> listaItemModel = new ArrayList<>();
        listaItemModel.add(itemCardapioModel);
        when(repository.findByCardapioModelUuid(any(UUID.class))).thenReturn(listaItemModel);

        var listaEncontrada = repository.findByCardapioModelUuid(uuid);

        assertThat(listaEncontrada).isNotNull();
        Assertions.assertEquals(listaEncontrada.size(),listaItemModel.size());
        Assertions.assertEquals(listaEncontrada,listaItemModel);
    }
    @Test
    public void deveRetornarListaVaziaQuandoNaoExistiremItensParaOCardapio() {
        var uuid = UUID.randomUUID();
        List<ItemCardapioModel> listaItemCardapio = new ArrayList<>();
        when(repository.findByCardapioModelUuid(any(UUID.class))).thenReturn(listaItemCardapio);

        var listaEncontrada = repository.findByCardapioModelUuid(uuid);

        assertThat(listaEncontrada).isNotNull();
        assertThat(listaEncontrada).isEqualTo(listaItemCardapio);
        verify(repository, times(1)).findByCardapioModelUuid(uuid);
    }
    @Test
    public void deveCriarItemCardapio(){
        ItemCardapioModel itemCardapioModel = criarItemCardapio();
        when(repository.save(any(ItemCardapioModel.class))).thenReturn(itemCardapioModel);

        var itemCriado = repository.save(itemCardapioModel);

        assertThat(itemCriado).isNotNull().isEqualTo(itemCardapioModel);
        verify(repository,times(1)).save(itemCriado);
    }
    @Test
    public void deveLancarExcecaoAoTentarCriarItemCardapioInvalido() {
        ItemCardapioModel itemInvalido = criarItemCardapio();
        when(repository.save(any(ItemCardapioModel.class)))
                .thenThrow(new DataIntegrityViolationException("Erro de integridade dos dados"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(itemInvalido);
        });
        verify(repository, times(1)).save(itemInvalido);
    }
    @Test
    public void deveDeletarItensPorCardapio(){
        ItemCardapioModel itemCardapioModel = criarItemCardapio();
        List<ItemCardapioModel> listaItemModel = new ArrayList<>();
        listaItemModel.add(itemCardapioModel);
        doNothing().when(repository).deleteByCardapioModelUuid(any(UUID.class));

        repository.deleteById(itemCardapioModel.getCardapioModel().getUuid());

        verify(repository,times(1)).deleteById(itemCardapioModel.getCardapioModel().getUuid());
    }
    @Test
    public void deveLancarExcecaoAoTentarDeletarItensDeUmCardapioInexistente() {
        var uuid = UUID.randomUUID();
        doThrow(new EmptyResultDataAccessException(1))
                .when(repository).deleteByCardapioModelUuid(any(UUID.class));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteByCardapioModelUuid(uuid);
        });

        verify(repository, times(1)).deleteByCardapioModelUuid(uuid);
    }
    private ItemCardapioModel criarItemCardapio(){
        CardapioModel cardapioModel = new CardapioModel();
        cardapioModel.setNome("Cardapio Teste");
        DisponibilidadePedido disponibilidadePedido = DisponibilidadePedido.RESTAURANTE;
        return new ItemCardapioModel(
                "nomeI",
                "descricaoI",
                10.00,
                disponibilidadePedido,
                "imagemI.png",
                cardapioModel
        );
    }
}

package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CardapioRepositoryTest {
    @Mock
    private CardapioModelRepository repository;
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
    public void deveBuscarCardapioPorUuid(){
        CardapioModel cardapioModel = new CardapioModel();
        cardapioModel.setNome("Cardapio");
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(cardapioModel));

        var cardapioEncontrado = repository.findById(cardapioModel.getUuid());

        assertThat(cardapioEncontrado).isNotNull();
        verify(repository,times(1)).findById(cardapioModel.getUuid());
    }
    @Test
    public void naoDeveBuscarCardapioPorUuidInexistente(){
        var uuid = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var cardapioNaoEncontrado = repository.findById(uuid);

        assertThat(cardapioNaoEncontrado).isEmpty();
        verify(repository, times(1)).findById(uuid);
    }
    @Test
    public void deveBuscarCardapioPorRestauranteId(){
        CardapioModel cardapioModel = new CardapioModel();
        cardapioModel.setNome("Cardapio");

        when(repository.findByUuidRestaurante(any(UUID.class))).thenReturn(cardapioModel);

        var cardapioEncontrado = repository.findByUuidRestaurante(UUID.fromString("26b43f40-c14e-447f-9fda-c5f90f8887b2"));

        assertThat(cardapioEncontrado).isNotNull().isEqualTo(cardapioModel);
        verify(repository,times(1)).findByUuidRestaurante(UUID.fromString("26b43f40-c14e-447f-9fda-c5f90f8887b2"));
    }

    @Test
    public void naoDeveBuscarCardapioPorRestauranteIdInexistente(){
        var uuid = UUID.randomUUID();
        when(repository.findByUuidRestaurante(any(UUID.class))).thenReturn(null);

        var cardapioEncontrado = repository.findByUuidRestaurante(uuid);

        assertThat(cardapioEncontrado).isNull();
        verify(repository, times(1)).findByUuidRestaurante(uuid);
    }

    @Test
    public void deveSalvarCardapio(){
        CardapioModel cardapioModel = new CardapioModel();
        cardapioModel.setNome("Cardapio Teste");
        when(repository.save(any(CardapioModel.class))).thenReturn(cardapioModel);

        var cardapioSalvo = repository.save(cardapioModel);

        assertThat(cardapioSalvo).isNotNull().isEqualTo(cardapioModel);
        verify(repository,times(1)).save(cardapioSalvo);
    }
    @Test
    public void deveLancarExcecaoAoSalvarCardapioInvalido(){
        CardapioModel cardapioInvalido = new CardapioModel();
        cardapioInvalido.setNome(null);
        when(repository.save(any(CardapioModel.class)))
                .thenThrow(new DataIntegrityViolationException("Violação de constraint"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(cardapioInvalido);
        });

        verify(repository, times(1)).save(cardapioInvalido);
    }
    @Test
    public void deveExcluirCardapio(){
        CardapioModel cardapioModel = new CardapioModel();
        doNothing().when(repository).deleteById(any(UUID.class));

        repository.deleteById(cardapioModel.getUuid());

        verify(repository,times(1)).deleteById(cardapioModel.getUuid());
    }
    @Test
    public void deveLancarExcecaoAoExcluirCardapioInexistente(){
        var uuid = UUID.randomUUID();
        doThrow(new EmptyResultDataAccessException(1))
                .when(repository).deleteById(any(UUID.class));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(uuid);
        });

        verify(repository, times(1)).deleteById(uuid);
    }
}

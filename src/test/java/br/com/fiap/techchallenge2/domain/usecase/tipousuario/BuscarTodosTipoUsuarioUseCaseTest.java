package br.com.fiap.techchallenge2.domain.usecase.tipousuario;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.exception.AcessoNegadoException;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarTodosTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioInterface tipoUsuarioInterface;

    private BuscarTodosTipoUsuarioUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscarTodosTipoUsuarioUseCase(tipoUsuarioInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioLogadoNaoForAdmin() {
        assertThrows(AcessoNegadoException.class, () -> useCase.execute("Cliente"));
        verify(tipoUsuarioInterface, never()).buscarTodosTiposUsuario();
    }

    @Test
    void deveRetornarListaDeTipoUsuarioOutputQuandoAdmin() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        TipoUsuario tipo1 = mock(TipoUsuario.class);
        TipoUsuario tipo2 = mock(TipoUsuario.class);
        when(tipo1.getId()).thenReturn(id1);
        when(tipo1.getNome()).thenReturn("Admin");
        when(tipo2.getId()).thenReturn(id2);
        when(tipo2.getNome()).thenReturn("Cliente");
        when(tipoUsuarioInterface.buscarTodosTiposUsuario()).thenReturn(List.of(tipo1, tipo2));

        List<TipoUsuarioOutput> result = useCase.execute("Admin");

        assertEquals(2, result.size());
        assertEquals(id1, result.get(0).getId());
        assertEquals("Admin", result.get(0).getNome());
        assertEquals(id2, result.get(1).getId());
        assertEquals("Cliente", result.get(1).getNome());
        verify(tipoUsuarioInterface).buscarTodosTiposUsuario();
    }
}


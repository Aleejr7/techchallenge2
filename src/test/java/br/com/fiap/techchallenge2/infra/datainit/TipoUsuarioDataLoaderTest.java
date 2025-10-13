package br.com.fiap.techchallenge2.infra.datainit;

import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TipoUsuarioDataLoaderTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @InjectMocks
    private TipoUsuarioDataLoader tipoUsuarioDataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarTipoUsuarioQuandoRepositorioEstaVazio() {
        when(tipoUsuarioRepository.count()).thenReturn(0L);
        ArgumentCaptor<TipoUsuarioModel> captor = ArgumentCaptor.forClass(TipoUsuarioModel.class);

        tipoUsuarioDataLoader.init();

        Mockito.verify(tipoUsuarioRepository, times(1)).save(captor.capture());
        TipoUsuarioModel tipoUsuarioSalvo = captor.getValue();

        assertEquals("Admin", tipoUsuarioSalvo.getNome());
    }

    @Test
    void naoDeveSalvarQuandoRepositorioNaoEstaVazio() {
        when(tipoUsuarioRepository.count()).thenReturn(5L);

        tipoUsuarioDataLoader.init();

        Mockito.verify(tipoUsuarioRepository, never()).save(any(TipoUsuarioModel.class));
    }
}

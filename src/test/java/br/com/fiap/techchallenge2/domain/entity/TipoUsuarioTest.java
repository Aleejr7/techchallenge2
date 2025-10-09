// src/test/java/br/com/fiap/techchallenge2/domain/entity/TipoUsuarioTest.java
package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioTest {

    @Test
    void deveCriarTipoUsuarioComNomeValido() throws DadoVazioException {
        TipoUsuario tipoUsuario = new TipoUsuario("Administrador");
        assertEquals("Administrador", tipoUsuario.getNome());
        assertNull(tipoUsuario.getId());
    }

    @Test
    void deveCriarTipoUsuarioComIdENomeValido() throws DadoVazioException {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoUsuario = new TipoUsuario(id, "Cliente");
        assertEquals("Cliente", tipoUsuario.getNome());
        assertEquals(id, tipoUsuario.getId());
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazioNoConstrutor() {
        assertThrows(DadoVazioException.class, () -> new TipoUsuario(""));
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazioNoSetNome() throws DadoVazioException {
        TipoUsuario tipoUsuario = new TipoUsuario("Valido");
        assertThrows(DadoVazioException.class, () -> tipoUsuario.setNome(""));
    }

    @Test
    void deveAlterarNomeComValorValido() throws DadoVazioException {
        TipoUsuario tipoUsuario = new TipoUsuario("Original");
        tipoUsuario.setNome("NovoNome");
        assertEquals("NovoNome", tipoUsuario.getNome());
    }
}

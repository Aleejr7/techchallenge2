// src/test/java/br/com/fiap/techchallenge2/domain/entity/UsuarioTest.java
package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoInvalidoException;
import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private TipoUsuario tipoUsuarioValido() throws DadoVazioException {
        return new TipoUsuario("Cliente");
    }

    @Test
    void deveCriarUsuarioComDadosValidos() throws DadoVazioException {
        Usuario usuario = new Usuario(
                "João Silva",
                "12345678901",
                "joao@email.com",
                "senha123",
                "11999999999",
                "Rua A, 123",
                tipoUsuarioValido()
        );
        assertEquals("João Silva", usuario.getNome());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("joao@email.com", usuario.getEmail());
        assertNotEquals("senha123", usuario.getSenha()); // senha deve estar encriptada
        assertEquals("11999999999", usuario.getTelefone());
        assertEquals("Rua A, 123", usuario.getEndereco());
        assertEquals("Cliente", usuario.getTipoUsuario().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        assertThrows(DadoVazioException.class, () -> new Usuario(
                "",
                "12345678901",
                "joao@email.com",
                "senha123",
                "11999999999",
                "Rua A, 123",
                tipoUsuarioValido()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoCpfInvalido() {
        assertThrows(DadoInvalidoException.class, () -> new Usuario(
                "João Silva",
                "123",
                "joao@email.com",
                "senha123",
                "11999999999",
                "Rua A, 123",
                tipoUsuarioValido()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoEmailInvalido() {
        assertThrows(DadoInvalidoException.class, () -> new Usuario(
                "João Silva",
                "12345678901",
                "joaoemail.com",
                "senha123",
                "11999999999",
                "Rua A, 123",
                tipoUsuarioValido()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoSenhaCurta() {
        assertThrows(DadoInvalidoException.class, () -> new Usuario(
                "João Silva",
                "12345678901",
                "joao@email.com",
                "123",
                "11999999999",
                "Rua A, 123",
                tipoUsuarioValido()
        ));
    }

    @Test
    void deveAlterarNomeComValorValido() throws DadoVazioException {
        Usuario usuario = new Usuario(
                "João Silva",
                "12345678901",
                "joao@email.com",
                "senha123",
                "11999999999",
                "Rua A, 123",
                tipoUsuarioValido()
        );
        usuario.setNome("Maria");
        assertEquals("Maria", usuario.getNome());
    }

    @Test
    void deveLancarExcecaoAoAlterarTelefoneParaInvalido() throws DadoVazioException {
        Usuario usuario = new Usuario(
                "João Silva",
                "12345678901",
                "joao@email.com",
                "senha123",
                "11999999999",
                "Rua A, 123",
                tipoUsuarioValido()
        );
        assertThrows(DadoInvalidoException.class, () -> usuario.setTelefone("123"));
    }
}
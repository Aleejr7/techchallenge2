// src/test/java/br/com/fiap/techchallenge2/domain/entity/RestauranteTest.java
package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import br.com.fiap.techchallenge2.domain.exception.DadoInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    private Usuario donoRestaurante() throws DadoVazioException {
        return new Usuario(
                "Dono",
                "12345678901",
                "dono@email.com",
                "senha123",
                "11999999999",
                "Rua B, 456",
                new TipoUsuario("Administrador")
        );
    }

    @Test
    void deveCriarRestauranteComDadosValidos() throws DadoVazioException {
        Restaurante restaurante = new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "08:00",
                "22:00",
                donoRestaurante()
        );
        assertEquals("Restaurante A", restaurante.getNome());
        assertEquals("Rua X, 100", restaurante.getEndereco());
        assertEquals("Italiana", restaurante.getTipoCozinha());
        assertEquals("08:00", restaurante.getHorarioFuncionamento().horarioAbertura().toString());
        assertEquals("22:00", restaurante.getHorarioFuncionamento().horarioFechamento().toString());
        assertEquals("Dono", restaurante.getDonoRestaurante().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        assertThrows(DadoVazioException.class, () -> new Restaurante(null,
                "",
                "Rua X, 100",
                "Italiana",
                "08:00",
                "22:00",
                donoRestaurante()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoVazio() {
        assertThrows(DadoVazioException.class, () -> new Restaurante(null,
                "Restaurante A",
                "",
                "Italiana",
                "08:00",
                "22:00",
                donoRestaurante()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoTipoCozinhaVazio() {
        assertThrows(DadoVazioException.class, () -> new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "",
                "08:00",
                "22:00",
                donoRestaurante()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoHorarioAberturaVazio() {
        assertThrows(DadoVazioException.class, () -> new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "",
                "22:00",
                donoRestaurante()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoHorarioFechamentoVazio() {
        assertThrows(DadoVazioException.class, () -> new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "08:00",
                "",
                donoRestaurante()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoHorarioAberturaInvalido() {
        assertThrows(DadoInvalidoException.class, () -> new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "8h",
                "22:00",
                donoRestaurante()
        ));
    }

    @Test
    void deveLancarExcecaoQuandoHorarioFechamentoInvalido() {
        assertThrows(DadoInvalidoException.class, () -> new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "08:00",
                "22h",
                donoRestaurante()
        ));
    }

    @Test
    void deveAlterarNomeComValorValido() throws DadoVazioException {
        Restaurante restaurante = new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "08:00",
                "22:00",
                donoRestaurante()
        );
        restaurante.setNome("Restaurante B");
        assertEquals("Restaurante B", restaurante.getNome());
    }

    @Test
    void deveLancarExcecaoAoAlterarNomeParaVazio() throws DadoVazioException {
        Restaurante restaurante = new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "08:00",
                "22:00",
                donoRestaurante()
        );
        assertThrows(DadoVazioException.class, () -> restaurante.setNome(""));
    }

    @Test
    void deveAlterarHorarioFuncionamentoComValoresValidos() throws DadoVazioException {
        Restaurante restaurante = new Restaurante(null,
                "Restaurante A",
                "Rua X, 100",
                "Italiana",
                "08:00",
                "22:00",
                donoRestaurante()
        );
        restaurante.setHorarioFuncionamento("09:00", "23:00");
        assertEquals("09:00", restaurante.getHorarioFuncionamento().horarioAbertura().toString());
        assertEquals("23:00", restaurante.getHorarioFuncionamento().horarioFechamento().toString());
    }
}

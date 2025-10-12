package br.com.fiap.techchallenge2.infra.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CardapioModelTest {

    @Test
    public void deveCriarCardapioComConstrutorPadraoESetarNome(){
        var nomeCardapio = "Nome cardapio";
        var cardapioModel = new CardapioModel();
        cardapioModel.setNome(nomeCardapio);

        assertThat(cardapioModel).isInstanceOf(CardapioModel.class);
        assertThat(cardapioModel.getNome()).isEqualTo(nomeCardapio);
    }
    @Test
    public void deveCriarCardapioComConstrutorPadrao(){
        var cardapioModel = new CardapioModel();

        assertThat(cardapioModel).isInstanceOf(CardapioModel.class);
    }
}

package br.com.fiap.techchallenge2.infra.model;

import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ItemCardapioTest {

    @Test
    public void deveCriarItemCardapioComConstrutorPadraoESetandoOsCampos(){
        var nome = "agua";
        var desc = "agua mineralgelada";
        var preco = 10.9;
        var foto = "agua mineralgelada.png";
        var cardapioModel = new CardapioModel();
        DisponibilidadePedido disponibilidadePedido = DisponibilidadePedido.RESTAURANTE;
        ItemCardapioModel itemCardapioModel = new ItemCardapioModel();

        itemCardapioModel.setNome(nome);
        itemCardapioModel.setDescricao(desc);
        itemCardapioModel.setPreco(preco);
        itemCardapioModel.setDisponibilidadePedido(disponibilidadePedido);
        itemCardapioModel.setImagemUrl(foto);
        itemCardapioModel.setCardapioModel(cardapioModel);

        assertThat(itemCardapioModel).satisfies(i -> {
            assertThat(i.getNome()).isEqualTo(nome);
            assertThat(i.getDescricao()).isEqualTo(desc);
            assertThat(i.getPreco()).isEqualTo(preco);
            assertThat(i.getImagemUrl()).isEqualTo(foto);
            assertThat(i.getCardapioModel()).isEqualTo(cardapioModel);
        });
    }

    @Test
    public void deveCriarItemCardapioComConstrutorPadrao(){
        var itemCardapioModel = new ItemCardapioModel();

        assertThat(itemCardapioModel).isInstanceOf(ItemCardapioModel.class);
    }
}

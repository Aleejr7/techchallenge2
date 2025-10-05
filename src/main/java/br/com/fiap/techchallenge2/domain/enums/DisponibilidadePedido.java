package br.com.fiap.techchallenge2.domain.enums;

public enum DisponibilidadePedido
{
    RESTAURANTE;
    //DELIVERY,
    //RESTAURANTE_E_DELIVERY;

    public String getDescricao() {
        switch (this) {
            case RESTAURANTE:
                return "Disponibilidade para pedido apenas no restaurante";
            //case DELIVERY:
            //    return "Disponibilidade para pedido apenas por delivery";
            //case RESTAURANTE_E_DELIVERY:
            //    return "Disponibilidade para pedido no restaurante e por delivery";
            default:
                return "";
        }
    }

}

package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.enums.DisponibilidadePedido;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CardapioAdapterRepository implements CardapioInterface {
    CardapioModelRepository cardapioModelRepository;
    ItemCardapioModelRepository itemCardapioModelRepository;

    @Override
    public Cardapio buscarCardapioPorUuid(UUID uuid) {
        CardapioModel cardapioModel = cardapioModelRepository.findById(uuid)
                .orElse(null);

        Cardapio cardapio = new Cardapio(
                cardapioModel.getNome(),
                cardapioModel.getUuidRestaurante()
        );

        cardapio.setUuid(cardapioModel.getUuid());

        List<ItemCardapio> itens = cardapioModel.getItensCardapio().stream()
                .map(itemModel -> new ItemCardapio(
                        itemModel.getNome(),
                        itemModel.getDescricao(),
                        itemModel.getPreco(),
                        itemModel.getDisponibilidadePedido(),
                        itemModel.getImagemUrl(),
                        cardapioModel.getUuid()
                ))
                .toList();
        cardapio.setItensCardapio(itens);
        return cardapio;
    }

    @Override
    public Cardapio adicionarItemAoCardapio(UUID uuidCardapio, UUID uuidItem) {
        return null;
    }

    @Override
    public void removerItemDoCardapio(UUID uuidCardapio, UUID uuidItem) {
        CardapioModel cardapioModel = cardapioModelRepository.findById(uuidCardapio).orElse(null);
        ItemCardapioModel itemCardapioModel = itemCardapioModelRepository.findById(uuidItem).orElse(null);
        itemCardapioModelRepository.deleteById(itemCardapioModel.getUuid());
    }

    @Override
    public Cardapio atualizarCardapio(Cardapio cardapio) {
        return null;
    }

    @Override
    public Cardapio buscarCardapioPorUUidRestaurante(UUID uuidRestaurante) {
        CardapioModel cardapioModel = cardapioModelRepository.findByUuidRestaurante(uuidRestaurante);

        Cardapio cardapio = new Cardapio(
                cardapioModel.getNome(),
                cardapioModel.getUuidRestaurante()
        );
        cardapio.setUuid(cardapioModel.getUuid());

        List<ItemCardapio> itens = cardapioModel.getItensCardapio().stream()
                .map(itemModel -> new ItemCardapio(
                        itemModel.getNome(),
                        itemModel.getDescricao(),
                        itemModel.getPreco(),
                        itemModel.getDisponibilidadePedido(),
                        itemModel.getImagemUrl(),
                        cardapioModel.getUuid()
                ))
                .toList();
        cardapio.setItensCardapio(itens);
        return cardapio;
    }

    @Override
    public void deletarCardapioPorUuid(UUID uuid) {
        CardapioModel cardapioModel = cardapioModelRepository.findById(uuid).orElse(null);
        cardapioModelRepository.deleteById(uuid);
    }
}

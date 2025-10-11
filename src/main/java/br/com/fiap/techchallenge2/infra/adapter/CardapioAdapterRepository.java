package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.RestauranteModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CardapioAdapterRepository implements CardapioInterface {
    private final CardapioModelRepository cardapioModelRepository;
    private final ItemCardapioModelRepository itemCardapioModelRepository;

    @Override
    public Cardapio buscarCardapioPorUuid(UUID uuid) {
        CardapioModel cardapioModel = cardapioModelRepository.findById(uuid)
                .orElse(null);

        if (cardapioModel == null) return null;

        Cardapio cardapio = new Cardapio(
                cardapioModel.getNome(),
                cardapioModel.getUuidRestaurante()
        );
        cardapio.setUuid(cardapioModel.getUuid());

        List<ItemCardapio> itens = cardapioModel.getItensCardapio().stream()
                .map(itemModel -> new ItemCardapio(
                        itemModel.getUuid(),
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
        CardapioModel cardapioModel = cardapioModelRepository.findById(uuidCardapio).orElse(null);
        ItemCardapioModel itemCardapioModel = itemCardapioModelRepository.findById(uuidItem).orElse(null);

        ItemCardapio itemCardapioEntity = new ItemCardapio(
                itemCardapioModel.getNome(),
                itemCardapioModel.getDescricao(),
                itemCardapioModel.getPreco(),
                itemCardapioModel.getDisponibilidadePedido(),
                itemCardapioModel.getImagemUrl(),
                cardapioModel.getUuid()
        );

        cardapioModel.getItensCardapio().add(itemCardapioModel);
        itemCardapioModel.setCardapioModel(cardapioModel);
        
        var cardapioAtualizado = cardapioModelRepository.save(cardapioModel);
        Cardapio cardapioEntity = new Cardapio(cardapioAtualizado.getNome(),cardapioAtualizado.getUuidRestaurante());

        cardapioEntity.setUuid(cardapioAtualizado.getUuid());
        cardapioEntity.getItensCardapio().add(itemCardapioEntity);
        cardapioEntity.setItensCardapio(cardapioEntity.getItensCardapio());

        return cardapioEntity;
    }

    @Override
    public void removerItemDoCardapio(UUID uuidCardapio, UUID uuidItem) {
        CardapioModel cardapioModel = cardapioModelRepository.findById(uuidCardapio).orElse(null);
        ItemCardapioModel itemCardapioModel = itemCardapioModelRepository.findById(uuidItem).orElse(null);
        itemCardapioModelRepository.deleteById(itemCardapioModel.getUuid());
    }

    @Override
    public Cardapio atualizarCardapio(Cardapio cardapio) {
        CardapioModel cardapioModel = cardapioModelRepository.findById(cardapio.getUuid()).orElse(null);
        cardapioModel.setNome(cardapio.getNome());
        cardapioModelRepository.save(cardapioModel);

        Cardapio cardapioEntity = new Cardapio(cardapioModel.getNome(), cardapioModel.getUuidRestaurante());
        cardapioEntity.setUuid(cardapioModel.getUuid());
        return cardapioEntity;
    }

    @Override
    public Cardapio buscarCardapioPorUUidRestaurante(UUID uuidRestaurante) {
        CardapioModel cardapioModel = cardapioModelRepository.findByUuidRestaurante(uuidRestaurante);

        if (cardapioModel == null) return null;

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

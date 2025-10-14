package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.Cardapio;
import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.gateway.CardapioInterface;
import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CardapioAdapterRepository implements CardapioInterface {
    private final CardapioModelRepository cardapioRepository;
    private final ItemCardapioModelRepository itemCardapioModelRepository;

    @Override
    public Cardapio criarCardapio(Restaurante restaurante, String nome) {
        CardapioModel cardapioModel = new CardapioModel();
        cardapioModel.setNome(nome);
        cardapioModel.setUuidRestaurante(restaurante.getUuid());
        cardapioRepository.save(cardapioModel);
        Cardapio cardapio = new Cardapio(
                cardapioModel.getNome(),
                cardapioModel.getUuidRestaurante()
        );
        cardapio.setUuid(cardapioModel.getUuid());
        return cardapio;
    }

    @Override
    public Cardapio buscarCardapioPorUuid(UUID uuid) {
        CardapioModel cardapioModel = cardapioRepository.findById(uuid)
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
        CardapioModel cardapioModel = cardapioRepository.findById(uuidCardapio).orElse(null);
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
        
        var cardapioAtualizado = cardapioRepository.save(cardapioModel);
        Cardapio cardapioEntity = new Cardapio(cardapioAtualizado.getNome(),cardapioAtualizado.getUuidRestaurante());

        cardapioEntity.setUuid(cardapioAtualizado.getUuid());
        cardapioEntity.getItensCardapio().add(itemCardapioEntity);
        cardapioEntity.setItensCardapio(cardapioEntity.getItensCardapio());

        return cardapioEntity;
    }

    @Transactional
    @Override
    public void removerItemDoCardapio(UUID uuidCardapio, UUID uuidItem) {
        CardapioModel cardapioModel = cardapioRepository.findById(uuidCardapio).orElse(null);
        ItemCardapioModel itemCardapioModel = itemCardapioModelRepository.findById(uuidItem).orElse(null);

        if (cardapioModel != null && itemCardapioModel != null) {
            cardapioModel.getItensCardapio().remove(itemCardapioModel);
            itemCardapioModel.setCardapioModel(null);
            cardapioModelRepository.save(cardapioModel);
            itemCardapioModelRepository.delete(itemCardapioModel);
        }
    }

    @Override
    public Cardapio atualizarCardapio(Cardapio cardapio) {
        CardapioModel cardapioModel = cardapioRepository.findById(cardapio.getUuid()).orElse(null);
        cardapioModel.setNome(cardapio.getNome());
        cardapioRepository.save(cardapioModel);

        Cardapio cardapioEntity = new Cardapio(cardapioModel.getNome(), cardapioModel.getUuidRestaurante());
        cardapioEntity.setUuid(cardapioModel.getUuid());
        return cardapioEntity;
    }

    @Override
    public Cardapio buscarCardapioPorUUidRestaurante(UUID uuidRestaurante) {
        CardapioModel cardapioModel = cardapioRepository.findByUuidRestaurante(uuidRestaurante);

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
        cardapioRepository.deleteById(uuid);
    }
}

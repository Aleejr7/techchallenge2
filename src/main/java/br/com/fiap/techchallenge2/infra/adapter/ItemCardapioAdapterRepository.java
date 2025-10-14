package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ItemCardapioAdapterRepository implements ItemCardapioInterface {

    private final ItemCardapioModelRepository repository;
    private final CardapioModelRepository cardapioModelRepository;

    @Override
    public ItemCardapio criarItemCardapio(ItemCardapio itemCardapio) {
        CardapioModel cardapioModel = cardapioModelRepository.findById(itemCardapio.getUuidCardapio()).orElse(null);
        if (cardapioModel == null) return null;
        ItemCardapioModel itemCardapioModel = new ItemCardapioModel(
                itemCardapio.getNome(),
                itemCardapio.getDescricao(),
                itemCardapio.getPreco(),
                itemCardapio.getDisponibilidadePedido(),
                itemCardapio.getImagemUrl(),
                cardapioModel
        );
        repository.save(itemCardapioModel);
        // Cardapio cardapioEntity = new Cardapio(cardapioModel.getNome(),cardapioModel.getUuidRestaurante());
        ItemCardapio itemCardapioEntity = new ItemCardapio(itemCardapioModel.getNome(),
                itemCardapioModel.getDescricao(),
                itemCardapioModel.getPreco(),
                itemCardapioModel.getDisponibilidadePedido(),
                itemCardapioModel.getImagemUrl(),
                cardapioModel.getUuid()
        );
        itemCardapioEntity.setUuid(itemCardapioModel.getUuid());
        return itemCardapioEntity;
    }

    @Override
    public ItemCardapio buscarItemCardapioPorUuid(UUID uuid) {
        ItemCardapioModel itemCardapioModel = repository.findById(uuid).orElse(null);
        if (itemCardapioModel == null) return null;
        ItemCardapio itemCardapio = new ItemCardapio(
                itemCardapioModel.getNome(),
                itemCardapioModel.getDescricao(),
                itemCardapioModel.getPreco(),
                itemCardapioModel.getDisponibilidadePedido(),
                itemCardapioModel.getImagemUrl(),
                itemCardapioModel.getCardapioModel().getUuid()
        );
        itemCardapio.setUuid(itemCardapioModel.getUuid());
        return itemCardapio;
    }

    @Override
    public ItemCardapio atualizarItemCardapio(ItemCardapio itemCardapio) {
        ItemCardapioModel itemCardapioModel = repository.findById(itemCardapio.getUuid()).orElse(null);
        itemCardapioModel.setNome(itemCardapio.getNome());
        itemCardapioModel.setPreco(itemCardapio.getPreco());
        itemCardapioModel.setImagemUrl(itemCardapioModel.getImagemUrl());
        itemCardapioModel.setDescricao(itemCardapio.getDescricao());
        itemCardapioModel.setDisponibilidadePedido(itemCardapio.getDisponibilidadePedido());

        repository.save(itemCardapioModel);

        ItemCardapio itemCardapioEntity = new ItemCardapio(
                itemCardapioModel.getNome(),
                itemCardapioModel.getDescricao(),
                itemCardapioModel.getPreco(),
                itemCardapioModel.getDisponibilidadePedido(),
                itemCardapioModel.getImagemUrl(),
                itemCardapioModel.getCardapioModel().getUuid()
        );
        itemCardapioEntity.setUuid(itemCardapio.getUuid());
        return itemCardapioEntity;
    }

    @Transactional
    @Override
    public void deletarItemCardapioPorUuid(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public void deletarTodosItensPorUuidCardapio(UUID uuidCardapio) {
        repository.deleteByCardapioModelUuid(uuidCardapio);
    }
}

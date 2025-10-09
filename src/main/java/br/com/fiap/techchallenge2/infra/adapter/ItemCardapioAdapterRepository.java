package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.ItemCardapio;
import br.com.fiap.techchallenge2.domain.gateway.ItemCardapioInterface;
import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ItemCardapioAdapterRepository implements ItemCardapioInterface {

    ItemCardapioModelRepository repository;
    @Override
    public ItemCardapio criarItemCardapio(ItemCardapio itemCardapio) {
        ItemCardapioModel itemCardapioModel = new ItemCardapioModel(
                itemCardapio.getNome(),
                itemCardapio.getDescricao(),
                itemCardapio.getPreco(),
                itemCardapio.getDisponibilidadePedido(),
                itemCardapio.getImagemUrl(),
                itemCardapio.getUuidCardapio()
        );
        repository.save(itemCardapioModel);
        ItemCardapio itemCardapioEntity = new ItemCardapio(itemCardapioModel.getNome(),
                itemCardapioModel.getDescricao(),
                itemCardapioModel.getPreco(),
                itemCardapioModel.getDisponibilidadePedido(),
                itemCardapioModel.getImagemUrl(),
                itemCardapioModel.getUuidCardapio()
        );
        itemCardapioEntity.setUuid(itemCardapioModel.getUuid());
        return itemCardapioEntity;
    }

    @Override
    public ItemCardapio buscarItemCardapioPorUuid(UUID uuid) {
        ItemCardapioModel itemCardapioModel = repository.findById(uuid).orElse(null);
        ItemCardapio itemCardapio = new ItemCardapio(
                itemCardapioModel.getNome(),
                itemCardapioModel.getDescricao(),
                itemCardapioModel.getPreco(),
                itemCardapioModel.getDisponibilidadePedido(),
                itemCardapioModel.getImagemUrl(),
                itemCardapioModel.getUuidCardapio()
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
                itemCardapioModel.getUuidCardapio()
        );
        itemCardapioEntity.setUuid(itemCardapio.getUuid());
        return itemCardapioEntity;
    }

    @Override
    public void deletarItemCardapioPorUuid(UUID uuid) {
        ItemCardapioModel itemCardapioModel = repository.findById(uuid).orElse(null);
        if ( itemCardapioModel != null){
            repository.deleteById(itemCardapioModel.getUuid());
        }
    }

    @Override
    public void deletarTodosItensPorUuidCardapio(UUID uuidCardapio) {
        List<ItemCardapioModel> itensCardapio = repository.findByUuidCardapio(uuidCardapio);
        if (itensCardapio != null){
            repository.deleteByCardapioUuid(uuidCardapio);
        }
    }
}

package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemCardapioModelRepository extends JpaRepository<ItemCardapioModel, UUID> {
    List<ItemCardapioModel> findByUuidCardapio(UUID uuidCardapio);

    void deleteByCardapioUuid(UUID cardapioUuid);
}

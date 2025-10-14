package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.ItemCardapioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ItemCardapioModelRepository extends JpaRepository<ItemCardapioModel, UUID> {

    List<ItemCardapioModel> findByCardapioModelUuid(UUID cardapioUuid);

    @Transactional
    void deleteById(UUID uuid);
//    @Modifying
//    @Transactional
//    void deleteByUuid(UUID uuid);

    @Modifying
    @Transactional
    void deleteByCardapioModelUuid(UUID cardapioUuid);
}

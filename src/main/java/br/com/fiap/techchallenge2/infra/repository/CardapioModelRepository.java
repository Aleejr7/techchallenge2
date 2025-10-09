package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.CardapioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardapioModelRepository extends JpaRepository<CardapioModel, UUID> {
    CardapioModel findByUuidRestaurante(UUID uuidRestaurante);
}

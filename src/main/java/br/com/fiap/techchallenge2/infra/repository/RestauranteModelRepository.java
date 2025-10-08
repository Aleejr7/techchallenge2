package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.RestauranteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestauranteModelRepository extends JpaRepository<RestauranteModel, UUID> {

    RestauranteModel findByNome(String nome);
}

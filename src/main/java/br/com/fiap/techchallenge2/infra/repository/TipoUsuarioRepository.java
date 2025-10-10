package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioModel, UUID> {

    Optional<TipoUsuarioModel> findById(UUID uuid);

    TipoUsuarioModel findByNome(String nome);
}

package br.com.fiap.techchallenge2.infra.repository;

import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioModelRepository extends JpaRepository<UsuarioModel,UUID> {
    UsuarioModel findByEmail(String email);
    List<UsuarioModel> findAllByTipoUsuarioModelId(UUID id);

    boolean existsByTipoUsuarioModelId(UUID uuid);
}

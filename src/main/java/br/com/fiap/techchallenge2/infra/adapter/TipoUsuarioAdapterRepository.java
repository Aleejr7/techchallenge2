package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TipoUsuarioAdapterRepository implements TipoUsuarioInterface {

    private final TipoUsuarioRepository repository;

    @Override
    public TipoUsuario buscarTipoUsuarioPorUuid( UUID uuid ) {
        Optional<TipoUsuarioModel> tpUsuarioModel = repository.findById( uuid );
        if (!tpUsuarioModel.isPresent()){
            return null;
        }
        return new TipoUsuario(tpUsuarioModel.get().getId() , tpUsuarioModel.get().getNome());
    }

    @Override
    public TipoUsuario buscarTipoUsuarioPorNome(String nome) {
        TipoUsuarioModel tpUsuarioModel = repository.findByNome(nome);
        if (tpUsuarioModel == null){
            return null;
        }
        return new TipoUsuario(tpUsuarioModel.getId(), tpUsuarioModel.getNome());
    }

    @Override
    public List<TipoUsuario> buscarTodosTiposUsuario() {
        List<TipoUsuarioModel> listaTipoUsuarioModel = repository.findAll();
        List<TipoUsuario> listaTipoUsuarioEntity = new ArrayList<>();
        for (TipoUsuarioModel tipoUsuarioModel : listaTipoUsuarioModel){
            TipoUsuario tpUsuarioEntity = new TipoUsuario(tipoUsuarioModel.getId(),tipoUsuarioModel.getNome());
            listaTipoUsuarioEntity.add(tpUsuarioEntity);
        }
        return listaTipoUsuarioEntity;
    }

    @Override
    public TipoUsuario criarTipoUsuario(TipoUsuario tipoUsuario) {
        TipoUsuarioModel tpUsuarioModel = new TipoUsuarioModel();
        tpUsuarioModel.setNome(tipoUsuario.getNome());
        TipoUsuarioModel tpUsuarioModelSalvo = repository.save(tpUsuarioModel);

        return new TipoUsuario(tpUsuarioModelSalvo.getId(),tpUsuarioModelSalvo.getNome());
    }

    @Override
    public void deletarTipoUsuarioPorUuid(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public TipoUsuario atualizarTipoUsuario(TipoUsuario tipoUsuario) {
        TipoUsuarioModel tpUsuarioModel = new TipoUsuarioModel(tipoUsuario.getId(), tipoUsuario.getNome());
        TipoUsuarioModel tpUsuarioModelSalvo = repository.save(tpUsuarioModel);

        return new TipoUsuario(tpUsuarioModelSalvo.getId(),tpUsuarioModelSalvo.getNome());
    }
}

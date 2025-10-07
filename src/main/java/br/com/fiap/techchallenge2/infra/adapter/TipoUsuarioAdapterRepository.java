package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TipoUsuarioAdapterRepository implements TipoUsuarioInterface {

    private final TipoUsuarioRepository repository;

    @Override
    public TipoUsuario buscarTipoUsuarioPorNome(String nome) {
        TipoUsuarioModel tpUsuarioModel = repository.findByNome(nome);
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

        return new TipoUsuario(tpUsuarioModelSalvo.getNome());
    }

    @Override
    public void deletarTipoUsuarioPorNome(String nome) {
        TipoUsuarioModel tpUsuarioModel = repository.findByNome(nome);
        if (tpUsuarioModel != null){
            repository.deleteByNome(tpUsuarioModel.getNome());
        }
    }

    @Override
    public TipoUsuario atualizarTipoUsuario(TipoUsuario tipoUsuario) {
        TipoUsuarioModel tpUsuarioModel = new TipoUsuarioModel(tipoUsuario.getId(), tipoUsuario.getNome());
        TipoUsuarioModel tpUsuarioModelSalvo = repository.save(tpUsuarioModel);

        return new TipoUsuario(tpUsuarioModelSalvo.getId(),tpUsuarioModelSalvo.getNome());
    }
}

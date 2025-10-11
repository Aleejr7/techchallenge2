package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.gateway.TipoUsuarioInterface;
import br.com.fiap.techchallenge2.domain.gateway.UsuarioInterface;
import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioAdapterRepository implements UsuarioInterface {

    private final UsuarioModelRepository repository;
    @Override
    public void deletarUsuario(UUID id) {
        UsuarioModel usuarioModel = repository.findById(id).orElse(null);
        repository.deleteById(id);
    }
    @Override
    public Usuario criarUsuario(Usuario usuario) {
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(usuario.getTipoUsuario().getId(),usuario.getTipoUsuario().getNome());
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(tipoUsuarioEntity.getId(),tipoUsuarioEntity.getNome());

        UsuarioModel usuarioModel = new UsuarioModel(
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTelefone(),
                usuario.getEndereco(),
                tipoUsuarioModel
        );

        UsuarioModel usuarioModelSalvo = repository.save(usuarioModel);

        return new Usuario(
                usuarioModelSalvo.getUuid(),
                usuarioModelSalvo.getNome(),
                usuarioModelSalvo.getCpf(),
                usuarioModelSalvo.getEmail(),
                usuarioModelSalvo.getSenha(),
                usuarioModelSalvo.getTelefone(),
                usuarioModelSalvo.getEndereco(),
                tipoUsuarioEntity
            );
    }
    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(usuario.getTipoUsuario().getId(),
                usuario.getTipoUsuario().getNome());
        TipoUsuarioModel tipoUsuarioModel = new TipoUsuarioModel(tipoUsuarioEntity.getId(), tipoUsuarioEntity.getNome());

        UsuarioModel usuarioModel = new UsuarioModel(
                usuario.getUuid(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTelefone(),
                usuario.getEndereco(),
                tipoUsuarioModel);
        UsuarioModel usuarioModelSalvo = repository.save(usuarioModel);

        return new Usuario(
                usuarioModelSalvo.getUuid(),
                usuarioModelSalvo.getNome(),
                usuarioModelSalvo.getCpf(),
                usuarioModelSalvo.getEmail(),
                usuarioModelSalvo.getSenha(),
                usuarioModelSalvo.getTelefone(),
                usuarioModelSalvo.getEndereco(),
                tipoUsuarioEntity
            );
    }
    @Override
    public Usuario buscarUsuarioPorUuid(UUID id) {
        UsuarioModel usuarioModel = repository.findById(id).orElse(null);
        TipoUsuarioModel tipoUsuarioModel = usuarioModel.getTipoUsuarioModel();
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(tipoUsuarioModel.getId(), tipoUsuarioModel.getNome());

        return new Usuario(usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                tipoUsuarioEntity);
    }
    @Override
    public List<Usuario> buscarTodosUsuarios() {
        List<UsuarioModel> listaUsuarioModel = repository.findAll();
        List<Usuario> listaUsuarioEntity = new ArrayList<>();
        for (UsuarioModel usuarioModel : listaUsuarioModel){
            TipoUsuarioModel tipoUsuarioModel = usuarioModel.getTipoUsuarioModel();
            TipoUsuario tipoUsuarioEntity = new TipoUsuario(tipoUsuarioModel.getId(), tipoUsuarioModel.getNome());

            Usuario usuarioEntity = new Usuario(
                    usuarioModel.getUuid(),
                    usuarioModel.getNome(),
                    usuarioModel.getCpf(),
                    usuarioModel.getEmail(),
                    usuarioModel.getSenha(),
                    usuarioModel.getTelefone(),
                    usuarioModel.getEndereco(),
                    tipoUsuarioEntity
            );
            listaUsuarioEntity.add(usuarioEntity);
        }
        return listaUsuarioEntity;
    }
    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        UsuarioModel usuarioModel = repository.findByEmail(email);
        if(usuarioModel == null){
            return null;
        }

        TipoUsuarioModel tipoUsuarioModel = usuarioModel.getTipoUsuarioModel();
        TipoUsuario tipoUsuarioEntity = new TipoUsuario(tipoUsuarioModel.getId(), tipoUsuarioModel.getNome());

        return new Usuario(usuarioModel.getNome(),
                usuarioModel.getCpf(),
                usuarioModel.getEmail(),
                usuarioModel.getSenha(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco(),
                tipoUsuarioEntity);
    }
}

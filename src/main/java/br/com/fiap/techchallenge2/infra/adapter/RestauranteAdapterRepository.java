package br.com.fiap.techchallenge2.infra.adapter;

import br.com.fiap.techchallenge2.domain.entity.Restaurante;
import br.com.fiap.techchallenge2.domain.entity.TipoUsuario;
import br.com.fiap.techchallenge2.domain.entity.Usuario;
import br.com.fiap.techchallenge2.domain.gateway.RestauranteInterface;
import br.com.fiap.techchallenge2.infra.model.HorarioFuncionamentoModel;
import br.com.fiap.techchallenge2.infra.model.RestauranteModel;
import br.com.fiap.techchallenge2.infra.model.UsuarioModel;
import br.com.fiap.techchallenge2.infra.repository.RestauranteModelRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RestauranteAdapterRepository implements RestauranteInterface {
    private final RestauranteModelRepository repository;
    private final UsuarioModelRepository usuarioRepository;

    @Override
    public Restaurante criarRestaurante(Restaurante restaurante) {
        UsuarioModel usuarioModel = usuarioRepository.findById(restaurante.getDonoRestaurante().getUuid()).orElse(null);
        HorarioFuncionamentoModel horario = new HorarioFuncionamentoModel
                (restaurante.getHorarioFuncionamento().horarioAbertura(),
                        restaurante.getHorarioFuncionamento().horarioFechamento());
        RestauranteModel restauranteModel = new RestauranteModel(
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                horario,
                usuarioModel
        );
        repository.save(restauranteModel);

        TipoUsuario tipoUsuario = new TipoUsuario(
                restauranteModel.getDonoRestaurante().getUuid(),
                restauranteModel.getDonoRestaurante().getNome());

        Usuario usuario = new Usuario(
                restauranteModel.getDonoRestaurante().getNome(),
                restauranteModel.getDonoRestaurante().getCpf(),
                restauranteModel.getDonoRestaurante().getEmail(),
                restauranteModel.getDonoRestaurante().getSenha(),
                restauranteModel.getDonoRestaurante().getTelefone(),
                restauranteModel.getDonoRestaurante().getEndereco(),
                tipoUsuario
        );


        return new Restaurante(restauranteModel.getUuid(),
                restauranteModel.getNome(),
                restauranteModel.getEndereco(),
                restauranteModel.getTipoCozinha(),
                restauranteModel.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteModel.getHorarioFuncionamento().horarioFechamento().toString(),
                usuario);
    }


    @Override
    public Restaurante buscarRestaurantePorUuid(UUID uuid) {
        RestauranteModel restauranteModel = repository.findById(uuid).orElse(null);
        if (restauranteModel == null) {
            return null;
        }

        TipoUsuario tipoUsuario = new TipoUsuario(
                restauranteModel.getDonoRestaurante().getUuid(),
                restauranteModel.getDonoRestaurante().getNome());

        Usuario usuarioEntity = new Usuario(
                restauranteModel.getDonoRestaurante().getUuid(),
                restauranteModel.getDonoRestaurante().getNome(),
                restauranteModel.getDonoRestaurante().getCpf(),
                restauranteModel.getDonoRestaurante().getEmail(),
                restauranteModel.getDonoRestaurante().getSenha(),
                restauranteModel.getDonoRestaurante().getTelefone(),
                restauranteModel.getDonoRestaurante().getEndereco(),
                tipoUsuario
        );

        return new Restaurante(restauranteModel.getUuid(),
                restauranteModel.getNome(),
                restauranteModel.getEndereco(),
                restauranteModel.getTipoCozinha(),
                restauranteModel.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteModel.getHorarioFuncionamento().horarioFechamento().toString(),
                usuarioEntity
        );
    }

    @Override
    public Restaurante buscarRestaurantePorNome(String nome) {
        RestauranteModel restauranteModel = repository.findByNome(nome);
        if (restauranteModel == null) {
            return null;
        }
        TipoUsuario tipoUsuario = new TipoUsuario(
                restauranteModel.getDonoRestaurante().getUuid(),
                restauranteModel.getDonoRestaurante().getNome());

        Usuario usuarioEntity = new Usuario(
                restauranteModel.getDonoRestaurante().getNome(),
                restauranteModel.getDonoRestaurante().getCpf(),
                restauranteModel.getDonoRestaurante().getEmail(),
                restauranteModel.getDonoRestaurante().getSenha(),
                restauranteModel.getDonoRestaurante().getTelefone(),
                restauranteModel.getDonoRestaurante().getEndereco(),
                tipoUsuario
        );

        return new Restaurante(restauranteModel.getUuid(),
                restauranteModel.getNome(),
                restauranteModel.getEndereco(),
                restauranteModel.getTipoCozinha(),
                restauranteModel.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteModel.getHorarioFuncionamento().horarioFechamento().toString(),
                usuarioEntity
        );
    }

    @Override
    public List<Restaurante> buscarTodosRestaurantes() {
        List<RestauranteModel> restaurantesModels = repository.findAll();
        List<Restaurante> ListaRestaurantesEntity = new ArrayList<>();
        for (RestauranteModel restauranteModel : restaurantesModels){
            TipoUsuario tipoUsuario = new TipoUsuario(
                    restauranteModel.getDonoRestaurante().getUuid(),
                    restauranteModel.getDonoRestaurante().getNome());

            Usuario usuarioEntity = new Usuario(
                    restauranteModel.getDonoRestaurante().getNome(),
                    restauranteModel.getDonoRestaurante().getCpf(),
                    restauranteModel.getDonoRestaurante().getEmail(),
                    restauranteModel.getDonoRestaurante().getSenha(),
                    restauranteModel.getDonoRestaurante().getTelefone(),
                    restauranteModel.getDonoRestaurante().getEndereco(),
                    tipoUsuario
            );

            Restaurante restauranteEntity = new Restaurante(
                    restauranteModel.getUuid(),
                    restauranteModel.getNome(),
                    restauranteModel.getEndereco(),
                    restauranteModel.getTipoCozinha(),
                    restauranteModel.getHorarioFuncionamento().horarioAbertura().toString(),
                    restauranteModel.getHorarioFuncionamento().horarioFechamento().toString(),
                    usuarioEntity
                    );
            ListaRestaurantesEntity.add(restauranteEntity);
        }
        return ListaRestaurantesEntity;
    }

    @Override
    public Restaurante atualizarRestaurante(Restaurante restaurante) {
        RestauranteModel restauranteModel = repository.findById(restaurante.getUuid()).orElse(null);
        if (restauranteModel == null) {
            return null;
        }

        HorarioFuncionamentoModel horarioNovo = new HorarioFuncionamentoModel(
                restaurante.getHorarioFuncionamento().horarioAbertura(),
                restaurante.getHorarioFuncionamento().horarioFechamento());

        RestauranteModel model = new RestauranteModel(
                restauranteModel.getUuid(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                horarioNovo,
                restauranteModel.getDonoRestaurante()
        );
        repository.save(model);

        TipoUsuario tipoUsuario = new TipoUsuario(
                restauranteModel.getDonoRestaurante().getUuid(),
                restauranteModel.getDonoRestaurante().getNome()
        );

        Usuario usuarioEntity = new Usuario(
                model.getDonoRestaurante().getNome(),
                model.getDonoRestaurante().getCpf(),
                model.getDonoRestaurante().getEmail(),
                model.getDonoRestaurante().getSenha(),
                model.getDonoRestaurante().getTelefone(),
                model.getDonoRestaurante().getEndereco(),
                tipoUsuario
        );

        return new Restaurante(
                model.getUuid(),
                model.getNome(),
                model.getEndereco(),
                model.getTipoCozinha(),
                model.getHorarioFuncionamento().horarioAbertura().toString(),
                model.getHorarioFuncionamento().horarioFechamento().toString(),
                usuarioEntity
        );
    }

    @Override
    public void deletarRestaurantePorUuid(UUID uuid) {
        repository.deleteById(uuid);
    }
}

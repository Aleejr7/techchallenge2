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
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class RestauranteAdapterRepository implements RestauranteInterface {
    RestauranteModelRepository repository;
    UsuarioModelRepository usuarioModelRepository;
    @Override
    public Restaurante criarRestaurante(Restaurante restaurante) {
        UsuarioModel usuarioModel = usuarioModelRepository.findById(restaurante.getDonoRestaurante().getUuid()).orElse(null);
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

        Usuario usuarioEntity = new Usuario(
                restauranteModel.getDonoRestaurante().getNome(),
                restauranteModel.getDonoRestaurante().getCpf(),
                restauranteModel.getDonoRestaurante().getEmail(),
                restauranteModel.getDonoRestaurante().getSenha(),
                restauranteModel.getDonoRestaurante().getTelefone(),
                restauranteModel.getDonoRestaurante().getEndereco(),
                tipoUsuario
        );


        return new Restaurante(restauranteModel.getNome(),
                restauranteModel.getEndereco(),
                restauranteModel.getTipoCozinha(),
                restauranteModel.getHorarioFuncionamento().horarioAbertura().toString(),
                restauranteModel.getHorarioFuncionamento().horarioFechamento().toString(),
                usuarioEntity);
    }

    @Override
    public Restaurante buscarRestaurantePorUuid(UUID uuid) {
        RestauranteModel restauranteModel = repository.findById(uuid).orElse(null);
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

        return new Restaurante(restauranteModel.getNome(),
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

        return new Restaurante(restauranteModel.getNome(),
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
    public void deletarRestaurantePorUuid(UUID uuid) {
        RestauranteModel restauranteModel = repository.findById(uuid).orElse(null);
        repository.deleteById(uuid);
    }
}

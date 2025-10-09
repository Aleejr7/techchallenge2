package br.com.fiap.techchallenge2.application.controller;


import br.com.fiap.techchallenge2.application.controller.request.RestauranteRequest;
import br.com.fiap.techchallenge2.domain.input.restaurante.AtualizarRestauranteInput;
import br.com.fiap.techchallenge2.domain.input.restaurante.CriarRestauranteInput;
import br.com.fiap.techchallenge2.domain.input.restaurante.DeletarRestauranteInput;
import br.com.fiap.techchallenge2.domain.output.restaurante.BuscarRestauranteOutput;
import br.com.fiap.techchallenge2.domain.output.restaurante.CriarRestauranteOutput;
import br.com.fiap.techchallenge2.domain.usecase.restaurante.*;
import br.com.fiap.techchallenge2.infra.adapter.RestauranteAdapterRepository;
import br.com.fiap.techchallenge2.infra.adapter.UsuarioAdapterRepository;
import br.com.fiap.techchallenge2.infra.repository.RestauranteModelRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.techchallenge2.application.controller.ApiPrefix.BASE;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE + "/restaurante")
public class RestauranteController {

    private final RestauranteModelRepository restauranteModelRepository;
    private final UsuarioModelRepository usuarioModelRepository;

    @GetMapping
    public ResponseEntity<List<BuscarRestauranteOutput>> buscarTodosRestaurantes() {

        BuscarTodosRestaurantesUseCase useCase = new BuscarTodosRestaurantesUseCase(
                new RestauranteAdapterRepository()
        );

        List<BuscarRestauranteOutput> buscarRestauranteOutput = useCase.execute();

        return ResponseEntity.status(200).body(buscarRestauranteOutput);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BuscarRestauranteOutput> buscarRestaurante(
            @PathVariable UUID uuid
            ) {
        BuscarRestauranteUseCase useCase = new BuscarRestauranteUseCase(
                new RestauranteAdapterRepository()
        );

        BuscarRestauranteOutput buscarRestauranteOutput = useCase.execute(uuid);

        return ResponseEntity.status(200).body(buscarRestauranteOutput);
    }

    @PostMapping
    public ResponseEntity<CriarRestauranteOutput> criarRestaurante(
            @RequestBody RestauranteRequest restauranteRequest
            ) {

        CriarRestauranteInput criarRestauranteInput = new CriarRestauranteInput(
                restauranteRequest.nome(),
                restauranteRequest.endereco(),
                restauranteRequest.tipoCozinha(),
                restauranteRequest.horarioAbertura(),
                restauranteRequest.horarioFechamento(),
                restauranteRequest.donoRestaurante()
        );

        CriarRestauranteUseCase useCase = new CriarRestauranteUseCase(
                new RestauranteAdapterRepository(), new UsuarioAdapterRepository( usuarioModelRepository )
        );

        CriarRestauranteOutput criarRestauranteOutput = useCase.execute(criarRestauranteInput);

        return ResponseEntity.status(201).body(criarRestauranteOutput);

    }


    @PutMapping("/{uuid}")
    public ResponseEntity<CriarRestauranteOutput> alterarRestaurante(
            @PathVariable UUID uuid,
            @RequestBody RestauranteRequest restauranteRequest
    ) {

        AtualizarRestauranteInput atualizarRestauranteInput = new AtualizarRestauranteInput(
                uuid,
                restauranteRequest.nome(),
                restauranteRequest.endereco(),
                restauranteRequest.tipoCozinha(),
                restauranteRequest.horarioAbertura(),
                restauranteRequest.horarioFechamento(),
                restauranteRequest.donoRestaurante()
        );

        AtualizarRestauranteUseCase useCase = new AtualizarRestauranteUseCase(
                new RestauranteAdapterRepository(), new UsuarioAdapterRepository( usuarioModelRepository )
        );

        useCase.execute(atualizarRestauranteInput);


        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletarRestaurante(
            @PathVariable UUID uuid,
            RestauranteRequest restauranteRequest
    ) {

        DeletarRestauranteInput deletarRestauranteInput = new DeletarRestauranteInput(
                uuid,
                restauranteRequest.donoRestaurante()
        );

        DeletarRestauranteUseCase useCase = new DeletarRestauranteUseCase(
                new RestauranteAdapterRepository(),
                new UsuarioAdapterRepository( usuarioModelRepository ),
                new CardapioAdapterRepository( cardapioModelRepository),
                new ItemCardapioRepository( cardapioModelRepository)
        );

        useCase.execute(deletarRestauranteInput);

        return ResponseEntity.status(200).build();

    }

}

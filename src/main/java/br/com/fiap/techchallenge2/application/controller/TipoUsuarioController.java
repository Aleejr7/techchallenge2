package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.AtualizarTipoUsuarioRequest;
import br.com.fiap.techchallenge2.application.controller.request.CriarTipoUsuarioRequest;
import br.com.fiap.techchallenge2.domain.input.tipousuario.AtualizarTipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.input.tipousuario.BuscarTipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.input.tipousuario.DeletarTipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import br.com.fiap.techchallenge2.domain.usecase.tipousuario.*;
import br.com.fiap.techchallenge2.infra.adapter.TipoUsuarioAdapterRepository;
import br.com.fiap.techchallenge2.infra.adapter.UsuarioAdapterRepository;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.techchallenge2.application.controller.ApiPrefix.BASE;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE + "/tipo-usuario")
public class TipoUsuarioController {

    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final UsuarioModelRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<TipoUsuarioOutput>> buscarTodosTipoUsuario(
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {

        BuscarTodosTipoUsuarioUseCase useCase = new BuscarTodosTipoUsuarioUseCase(new TipoUsuarioAdapterRepository(tipoUsuarioRepository));

        List<TipoUsuarioOutput> tipoUsuarioOutput = useCase.execute(tipoUsuarioLogado);

        return ResponseEntity.status( 200 ).body(tipoUsuarioOutput);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TipoUsuarioOutput> buscarTipoUsuarioPorUuid(
            @PathVariable UUID uuid,
            @RequestHeader ("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {
        BuscarTipoUsuarioInput tipoUsuarioInput = new BuscarTipoUsuarioInput(uuid, tipoUsuarioLogado);

        BuscarTipoUsuarioUseCase useCase = new BuscarTipoUsuarioUseCase(
                new TipoUsuarioAdapterRepository(tipoUsuarioRepository)
        );

        TipoUsuarioOutput tipoUsuarioOutput = useCase.execute(tipoUsuarioInput);

        return ResponseEntity.status( 200 ).body(tipoUsuarioOutput);

    }

    @PostMapping
    public ResponseEntity<TipoUsuarioOutput> criarTipoUsuario(
                @RequestBody CriarTipoUsuarioRequest tipoUsuarioRequest,
                @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado) {


        TipoUsuarioInput tipoUsuarioInput = new TipoUsuarioInput( tipoUsuarioRequest.nomeTipoUsuario(), tipoUsuarioLogado );

        CriarTipoUsuarioUseCase useCase = new CriarTipoUsuarioUseCase(
                new TipoUsuarioAdapterRepository( tipoUsuarioRepository )
        );

        TipoUsuarioOutput tipoUsuarioOutput = useCase.execute( tipoUsuarioInput );

        return ResponseEntity.status(HttpStatus.CREATED).body( tipoUsuarioOutput );
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<TipoUsuarioOutput> alterarTipoUsuario(
            @PathVariable UUID uuid,
            @RequestBody AtualizarTipoUsuarioRequest requestBody,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado) {

        AtualizarTipoUsuarioInput tipoUsuarioInput = new AtualizarTipoUsuarioInput(uuid, requestBody.nomeTipoUsuario(), tipoUsuarioLogado);

        AtualizarTipoUsuarioUseCase useCase = new AtualizarTipoUsuarioUseCase(
                new TipoUsuarioAdapterRepository(tipoUsuarioRepository)
        );

        TipoUsuarioOutput tipoUsuarioOutput = useCase.execute(tipoUsuarioInput);

        return ResponseEntity.status(HttpStatus.OK).body( tipoUsuarioOutput );
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletarTipoUsuario(
            @PathVariable UUID uuid,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {

        DeletarTipoUsuarioInput tipoUsuarioInput = new DeletarTipoUsuarioInput(uuid, tipoUsuarioLogado);

        DeletarTipoUsuarioUseCase useCase = new DeletarTipoUsuarioUseCase(
                new TipoUsuarioAdapterRepository(tipoUsuarioRepository),
                new UsuarioAdapterRepository(usuarioRepository)
        );

        useCase.execute(tipoUsuarioInput);

        return ResponseEntity.status(204).build();

    }
}

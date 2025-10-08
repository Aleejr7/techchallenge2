package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.CriarTipoUsuarioRequest;
import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import br.com.fiap.techchallenge2.domain.usecase.tipousuario.*;
import br.com.fiap.techchallenge2.infra.adapter.TipoUsuarioAdapterRepository;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static br.com.fiap.techchallenge2.application.controller.ApiPrefix.BASE;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE + "/tipo-usuario")
public class TipoUsuarioController {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    @GetMapping
    public ResponseEntity<List<TipoUsuarioOutput>> buscarTodosTipoUsuario(
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {

        BuscarTodosTipoUsuarioUseCase useCase = new BuscarTodosTipoUsuarioUseCase(new TipoUsuarioAdapterRepository(tipoUsuarioRepository));

        List<TipoUsuarioOutput> tipoUsuarioOutput = useCase.execute(tipoUsuarioLogado);

        return ResponseEntity.status( 200 ).body(tipoUsuarioOutput);

    }

    @GetMapping("/{nomeTipoUsuario}")
    public ResponseEntity<TipoUsuarioOutput> buscarTipoUsuario(
            @PathVariable String nomeTipoUsuario,
            @RequestHeader ("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {
        TipoUsuarioInput tipoUsuarioInput = new TipoUsuarioInput(nomeTipoUsuario, tipoUsuarioLogado);

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

        return ResponseEntity.status( 201 ).body( tipoUsuarioOutput );
    }

    @PutMapping("/{nomeTipoUsuario}")
    public ResponseEntity<TipoUsuarioOutput> alterarTipoUsuario(
            @PathVariable String nomeTipoUsuario,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado) {

        TipoUsuarioInput tipoUsuarioInput = new TipoUsuarioInput(nomeTipoUsuario, tipoUsuarioLogado);

        AtualizarTipoUsuarioUseCase useCase = new AtualizarTipoUsuarioUseCase(
                new TipoUsuarioAdapterRepository(tipoUsuarioRepository)
        );

        TipoUsuarioOutput tipoUsuarioOutput = useCase.execute(tipoUsuarioInput);

        return ResponseEntity.status(200).body( tipoUsuarioOutput );
    }

    @DeleteMapping("/{nomeTipoUsuario}")
    public ResponseEntity<Void> deletarTipoUsuario(
            @PathVariable String nomeTipoUsuario,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {

        TipoUsuarioInput tipoUsuarioInput = new TipoUsuarioInput(nomeTipoUsuario, tipoUsuarioLogado);

        DeletarTipoUsuarioUseCase useCase = new DeletarTipoUsuarioUseCase(
                new TipoUsuarioAdapterRepository(tipoUsuarioRepository)
        );

        useCase.execute(tipoUsuarioInput);

        return ResponseEntity.noContent().build();

    }








}

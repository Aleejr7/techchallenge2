package br.com.fiap.techchallenge2.application.controller;
import br.com.fiap.techchallenge2.application.controller.request.UsuarioRequest;
import br.com.fiap.techchallenge2.domain.input.usuario.*;
import br.com.fiap.techchallenge2.domain.output.usuario.UsuarioOutput;
import br.com.fiap.techchallenge2.domain.usecase.usuario.*;
import br.com.fiap.techchallenge2.infra.adapter.TipoUsuarioAdapterRepository;
import br.com.fiap.techchallenge2.infra.adapter.UsuarioAdapterRepository;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import br.com.fiap.techchallenge2.infra.repository.UsuarioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.techchallenge2.application.controller.ApiPrefix.BASE;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE + "/usuario")
public class UsuarioController {

    private final UsuarioModelRepository usuarioModelRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioOutput>> buscarTodosUsuario(
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {

        BuscarTodosUsuariosUseCase useCase = new BuscarTodosUsuariosUseCase(new UsuarioAdapterRepository(usuarioModelRepository));

        List<UsuarioOutput> usuarioOutput = useCase.execute(tipoUsuarioLogado);

        return ResponseEntity.status( 200 ).body(usuarioOutput);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UsuarioOutput> buscarUsuario(
            @PathVariable UUID uuid
    ) {
        BuscarUsuarioUseCase buscarUsuarioUseCase = new BuscarUsuarioUseCase(new UsuarioAdapterRepository(usuarioModelRepository));

        UsuarioOutput usuarioOutput = buscarUsuarioUseCase.execute(uuid);

        return ResponseEntity.status( 200 ).body(usuarioOutput);
    }



    @PostMapping
    public ResponseEntity<UsuarioOutput> criarUsuario(
            @RequestBody UsuarioRequest usuarioRequest
            ) {

        CriarUsuarioInput criarUsuarioInput = new CriarUsuarioInput(
                usuarioRequest.nome(),
                usuarioRequest.cpf(),
                usuarioRequest.email(),
                usuarioRequest.senha(),
                usuarioRequest.telefone(),
                usuarioRequest.endereco(),
                usuarioRequest.tipoUsuario()
        );

        CriarUsuarioUseCase useCase = new CriarUsuarioUseCase(
                new UsuarioAdapterRepository( usuarioModelRepository ), new TipoUsuarioAdapterRepository( tipoUsuarioRepository )
        );

        UsuarioOutput usuarioOutput = useCase.execute(criarUsuarioInput);

        return ResponseEntity.status( 201 ).body(usuarioOutput);

    }



    @PutMapping("/{uuid}")
    public ResponseEntity<UsuarioOutput> atualizarUsuario(
            @PathVariable UUID uuid,
            @RequestBody UsuarioRequest usuarioRequest
    ) {

        AtualizarUsuarioInput atualizarUsuarioInput = new AtualizarUsuarioInput(
                uuid,
                usuarioRequest.telefone(),
                usuarioRequest.endereco()
        );

        AtualizarUsuarioUseCase useCase = new AtualizarUsuarioUseCase(
                new UsuarioAdapterRepository(usuarioModelRepository)
        );

        UsuarioOutput usuarioOutput = useCase.execute(atualizarUsuarioInput);

        return ResponseEntity.status(200).body( usuarioOutput );
    }

    @PutMapping("/alterar-senha/{email}")
    public ResponseEntity<UsuarioOutput> alterarSenha(
            @PathVariable String email,
            @RequestBody UsuarioRequest usuarioRequest
    ) {
        AlterarSenhaDoUsuarioInput alterarSenhaDoUsuarioInput = new AlterarSenhaDoUsuarioInput(
                email,
                usuarioRequest.senhaAntiga(),
                usuarioRequest.senhaNova(),
                usuarioRequest.senhaNova()
        );

        AlterarSenhaUsuarioUseCase useCase = new AlterarSenhaUsuarioUseCase(
                new UsuarioAdapterRepository(usuarioModelRepository)
        );

        useCase.execute(alterarSenhaDoUsuarioInput);


        return ResponseEntity.status( 200 ).build();
    }

    @PutMapping("/alterar-tipoUsuario/{uuid}")
    public ResponseEntity<UsuarioOutput> alterarTipoUsuario(
            @PathVariable UUID uuid,
            @RequestBody UsuarioRequest usuarioRequest,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {

        AlterarTipoDoUsuarioInput alterarTipoDoUsuarioInput = new AlterarTipoDoUsuarioInput(
                uuid,
                usuarioRequest.tipoUsuario(),
                tipoUsuarioLogado
        );

        AlterarTipoDoUsuarioUseCase useCase = new AlterarTipoDoUsuarioUseCase(
                new TipoUsuarioAdapterRepository(tipoUsuarioRepository),
                new UsuarioAdapterRepository(usuarioModelRepository)
        );

        useCase.execute(alterarTipoDoUsuarioInput);


        return ResponseEntity.status( 200 ).build();
    }

    @DeleteMapping({"/{uuid}"})
    public ResponseEntity<Void> deletarUsuario(
            @PathVariable UUID uuid) {

        DeletarUsuarioUseCase useCase = new DeletarUsuarioUseCase(
                new UsuarioAdapterRepository(usuarioModelRepository)
        );

        useCase.execute(uuid);

        return ResponseEntity.status( 204 ).build();

    }




}

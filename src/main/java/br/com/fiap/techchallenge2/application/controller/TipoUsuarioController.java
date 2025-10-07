package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.domain.input.tipousuario.TipoUsuarioInput;
import br.com.fiap.techchallenge2.domain.output.tipousuario.TipoUsuarioOutput;
import br.com.fiap.techchallenge2.domain.usecase.tipousuario.CriarTipoUsuarioUseCase;
import br.com.fiap.techchallenge2.infra.adapter.TipoUsuarioAdapterRepository;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tipo-usuario")
public class TipoUsuarioController {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    @PostMapping
    public ResponseEntity<TipoUsuarioOutput> criarTipoUsuario(
                @RequestBody String nomeTipoUsuario,
                @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado) {


        TipoUsuarioInput tipoUsuarioInput = new TipoUsuarioInput( nomeTipoUsuario, tipoUsuarioLogado );

        CriarTipoUsuarioUseCase useCase = new CriarTipoUsuarioUseCase(
                new TipoUsuarioAdapterRepository( tipoUsuarioRepository )
        );

        TipoUsuarioOutput tipoUsuarioOutput = useCase.execute( tipoUsuarioInput );

        return ResponseEntity.status( 201 ).body( tipoUsuarioOutput );
    }
}

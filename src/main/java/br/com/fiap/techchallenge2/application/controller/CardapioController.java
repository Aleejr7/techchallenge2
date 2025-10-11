package br.com.fiap.techchallenge2.application.controller;

import br.com.fiap.techchallenge2.application.controller.request.CardapioRequest;
import br.com.fiap.techchallenge2.domain.input.cardapio.AlterarNomeCardapioInput;
import br.com.fiap.techchallenge2.domain.output.cardapio.CardapioOutput;
import br.com.fiap.techchallenge2.domain.usecase.cardapio.AlterarNomeCardapioUseCase;
import br.com.fiap.techchallenge2.domain.usecase.cardapio.BuscarCardapioUseCase;
import br.com.fiap.techchallenge2.infra.adapter.CardapioAdapterRepository;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.RestauranteModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.com.fiap.techchallenge2.application.controller.ApiPrefix.BASE;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE + "/cardapio")
public class CardapioController {

    private final CardapioModelRepository cardapioModelRepository;
    private final ItemCardapioModelRepository itemCardapioModelRepository;
    private final RestauranteModelRepository restauranteModelRepository;

    @GetMapping("/{uuid}")
    public ResponseEntity<CardapioOutput> buscarCardapio(
            @PathVariable UUID uuid) {

        BuscarCardapioUseCase buscarCardapioUseCase = new BuscarCardapioUseCase(
                new CardapioAdapterRepository(cardapioModelRepository, itemCardapioModelRepository)
        );

        CardapioOutput cardapioOutput = buscarCardapioUseCase.execute(uuid);

        return ResponseEntity.status(200).body(cardapioOutput);

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CardapioOutput> atualizarNomeCardapio(
            @PathVariable UUID uuid,
            @RequestBody CardapioRequest cardapioRequest,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {
        AlterarNomeCardapioInput alterarNomeCardapioInput = new AlterarNomeCardapioInput(
                cardapioRequest.uuid(),
                cardapioRequest.nome(),
                tipoUsuarioLogado
        );

        AlterarNomeCardapioUseCase alterarNomeCardapioUseCase = new AlterarNomeCardapioUseCase(
                new CardapioAdapterRepository(cardapioModelRepository, itemCardapioModelRepository)
        );

        CardapioOutput cardapioOutput = alterarNomeCardapioUseCase.execute(alterarNomeCardapioInput);

        return ResponseEntity.status(200).body(cardapioOutput);
    }
}

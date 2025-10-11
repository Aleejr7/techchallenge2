package br.com.fiap.techchallenge2.application.controller;


import br.com.fiap.techchallenge2.application.controller.request.ItemCardapioRequest;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.AtualizarItemCardapioInput;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.CriarItemCardapioInput;
import br.com.fiap.techchallenge2.domain.input.itemcardapio.DeletarItemCardapioInput;
import br.com.fiap.techchallenge2.domain.output.itemcardapio.ItemCardapioOutput;
import br.com.fiap.techchallenge2.domain.usecase.itemcardapio.AtualizarItemCardapioUseCase;
import br.com.fiap.techchallenge2.domain.usecase.itemcardapio.CriarItemCardapioUseCase;
import br.com.fiap.techchallenge2.domain.usecase.itemcardapio.DeletarItemCardapioUseCase;
import br.com.fiap.techchallenge2.infra.adapter.CardapioAdapterRepository;
import br.com.fiap.techchallenge2.infra.adapter.ItemCardapioAdapterRepository;
import br.com.fiap.techchallenge2.infra.repository.CardapioModelRepository;
import br.com.fiap.techchallenge2.infra.repository.ItemCardapioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.com.fiap.techchallenge2.application.controller.ApiPrefix.BASE;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE + "/itemcardapio")
public class ItemCardapioController {

    private final CardapioModelRepository cardapioRepository;
    private final ItemCardapioModelRepository itemCardapioRepository;

    @PostMapping
    public ResponseEntity<ItemCardapioOutput> criarItemCardapio(
            @RequestBody ItemCardapioRequest itemCardapioRequest,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
            ) {

        CriarItemCardapioInput criarItemCardapioInput = new CriarItemCardapioInput(
                itemCardapioRequest.nome(),
                itemCardapioRequest.descricao(),
                itemCardapioRequest.preco(),
                itemCardapioRequest.imagemUrl(),
                itemCardapioRequest.cardapioId(),
                tipoUsuarioLogado
        );

        CriarItemCardapioUseCase useCase = new CriarItemCardapioUseCase(
                new ItemCardapioAdapterRepository(itemCardapioRepository, cardapioRepository), new CardapioAdapterRepository(cardapioRepository, itemCardapioRepository)
        );

        ItemCardapioOutput itemCardapioOutput = useCase.execute(criarItemCardapioInput);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("{uuid}")
    public ResponseEntity<ItemCardapioOutput> atualizarItemCardapio(
            @PathVariable UUID uuid,
            @RequestBody ItemCardapioRequest itemCardapioRequest,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado
    ) {
        AtualizarItemCardapioInput atualizarItemCardapioInput = new AtualizarItemCardapioInput(
                uuid,
                itemCardapioRequest.nome(),
                itemCardapioRequest.descricao(),
                itemCardapioRequest.preco(),
                itemCardapioRequest.imagemUrl(),
                tipoUsuarioLogado
        );

        AtualizarItemCardapioUseCase useCase = new AtualizarItemCardapioUseCase(
                new ItemCardapioAdapterRepository(itemCardapioRepository, cardapioRepository)
        );

        useCase.execute(atualizarItemCardapioInput);


        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deletarItemCardapio(
            @PathVariable UUID uuid,
            @RequestHeader("TipoUsuarioLogado") String tipoUsuarioLogado,
            ItemCardapioRequest itemCardapioRequest) {

        DeletarItemCardapioInput deletarItemCardapioInput = new DeletarItemCardapioInput(
                itemCardapioRequest.uuid(),
                itemCardapioRequest.cardapioId(),
                tipoUsuarioLogado
        );

        DeletarItemCardapioUseCase useCase = new DeletarItemCardapioUseCase(
                new ItemCardapioAdapterRepository(itemCardapioRepository, cardapioRepository),
                new CardapioAdapterRepository(cardapioRepository, itemCardapioRepository)
        );

        useCase.execute(deletarItemCardapioInput);

        return ResponseEntity.status(204).build();
    }


}

package br.com.fiap.techchallenge2.domain.output.tipousuario;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class TipoUsuarioOutput {
    private final UUID id;
    private final String nome;
}
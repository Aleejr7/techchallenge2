package br.com.fiap.techchallenge2.domain.input.tipousuario;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record TipoUsuarioInput(
        String nome,
        String tipoUsuarioLogado
)
{
        // Permite desserializar a partir de um JSON string simples: "Cliente"
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TipoUsuarioInput from(String value) {
        return new TipoUsuarioInput(value, null);
    }

    // (Opcional) ao serializar, exporta só o nome
    @JsonValue
    public String toValue() {
        return nome;
    }
}

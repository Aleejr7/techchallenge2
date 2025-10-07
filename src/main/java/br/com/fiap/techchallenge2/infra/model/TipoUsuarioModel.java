package br.com.fiap.techchallenge2.infra.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tipoUsuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TipoUsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    public void setNome(String nome) {
        this.nome = nome;
    }

}

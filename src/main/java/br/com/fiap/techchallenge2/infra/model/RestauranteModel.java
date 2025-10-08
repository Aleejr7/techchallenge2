package br.com.fiap.techchallenge2.infra.model;

import br.com.fiap.techchallenge2.domain.entity.HorarioFuncionamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "restaurante")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private HorarioFuncionamento horarioFuncionamento;
    private UsuarioModel donoRestaurante;

    public RestauranteModel(String nome, String endereco, String tipoCozinha, HorarioFuncionamento horarioFuncionamento, UsuarioModel donoRestaurante) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.donoRestaurante = donoRestaurante;
    }
}

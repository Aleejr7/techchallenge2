package br.com.fiap.techchallenge2.infra.model;

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
    @Embedded
    private HorarioFuncionamentoModel horarioFuncionamento;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private UsuarioModel donoRestaurante;

    public RestauranteModel(String nome, String endereco, String tipoCozinha, HorarioFuncionamentoModel horarioFuncionamento, UsuarioModel donoRestaurante) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.donoRestaurante = donoRestaurante;
    }
}

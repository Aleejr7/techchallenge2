package br.com.fiap.techchallenge2.infra.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuarioModel tipoUsuarioModel;

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public TipoUsuarioModel getTipoUsuarioModel() {
        return tipoUsuarioModel;
    }

    public UsuarioModel(String nome, String cpf, String email, String senha, String telefone, String endereco, TipoUsuarioModel tipoUsuarioModel) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoUsuarioModel = tipoUsuarioModel;
    }
}

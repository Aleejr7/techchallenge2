package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoInvalidoException;
import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import br.com.fiap.techchallenge2.domain.utils.EncriptadorSenha;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
public class Usuario
{

    private UUID uuid;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
    @Setter
    private TipoUsuario tipoUsuario;

    public Usuario( String nome, String cpf, String email, String senha, String telefone, String endereco, TipoUsuario tipoUsuario )
    {
        validaNome( nome );
        validaCpf( cpf );
        validaEmail( email );
        validaSenha( senha );
        validaTelefone( telefone );
        validaEndereco( endereco );


        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = EncriptadorSenha.encriptar( senha );
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoUsuario = tipoUsuario;
    }

    public void setNome( String nome ){
        validaNome( nome );
        this.nome = nome;
    }

    public void setCpf( String cpf ){
        validaCpf( cpf );
        this.cpf = cpf;
    }

    public void setEmail( String email ){
        validaEmail( email );
        this.email = email;
    }

    public void setSenha( String senha ){
        validaSenha( senha );
        this.senha = EncriptadorSenha.encriptar( senha );
    }

    public void setTelefone( String telefone ){
        validaTelefone( telefone );
        this.telefone = telefone;
    }

    public void setEndereco( String endereco ){
        validaEndereco( endereco );
        this.endereco = endereco;
    }

    private void validaNome( String nome )
    {
        if( nome.isEmpty() ){
            throw new DadoVazioException("Nome do usuário não pode ser vazio.");
        }
    }

    private void validaCpf( String cpf )
    {
        if( cpf.isEmpty() ){
            throw new DadoVazioException("CPF do usuário não pode ser vazio.");
        }
        if (!cpf.matches("\\d{11}")) {
            throw new DadoInvalidoException("CPF deve conter 11 dígitos numéricos.");
        }
    }

    private void validaEmail( String email )
    {
        if( email.isEmpty() ){
            throw new DadoVazioException("Email do usuário não pode ser vazio.");
        }
        if ( !email.contains("@") || !email.contains(".") ) {
            throw new DadoInvalidoException("Email deve conter '@' e '.'.");
        }
    }

    private void validaSenha( String senha )
    {
        if( senha.isEmpty() ){
            throw new DadoVazioException("Senha do usuário não pode ser vazia.");
        }
        if ( senha.length() < 6 ) {
            throw new DadoInvalidoException("Senha deve ter pelo menos 6 caracteres.");
        }
    }

    private void validaTelefone( String telefone )
    {
        if( telefone.isEmpty() ){
            throw new DadoVazioException("Telefone do usuário não pode ser vazio.");
        }
        if (!telefone.matches("\\d{9,11}")) {
            throw new DadoInvalidoException("Número de telefone deve conter entre 9 à 11 dígitos numéricos.");
        }
    }

    private void validaEndereco( String endereco )
    {
        if( endereco.isEmpty() ){
            throw new DadoVazioException("Endereço do usuário não pode ser vazio.");
        }
    }
}

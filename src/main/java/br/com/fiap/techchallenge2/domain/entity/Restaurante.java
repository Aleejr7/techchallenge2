package br.com.fiap.techchallenge2.domain.entity;

import br.com.fiap.techchallenge2.domain.exception.DadoVazioException;
import br.com.fiap.techchallenge2.domain.exception.DadoInvalidoException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
@Data
public class Restaurante
{
    @Setter
    private UUID uuid;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private HorarioFuncionamento horarioFuncionamento;
    @Setter
    private Usuario donoRestaurante;
    private UUID cardapioId;

    public Restaurante(UUID uuid, String nome, String endereco, String tipoCozinha, String horarioAbertura, String horarioFechamento, Usuario donoRestaurante) {
        validaNome(nome);
        validaEndereco(endereco);
        validaTipoCozinha(tipoCozinha);
        validaHorarioFuncionamento(horarioAbertura, horarioFechamento);

        this.uuid = uuid;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = new HorarioFuncionamento (
            converterStringParaLocalTime( horarioAbertura ),
            converterStringParaLocalTime( horarioFechamento )
        );
        this.donoRestaurante = donoRestaurante;
    }

    private void validaHorarioFuncionamento( String horarioAbertura, String horarioFechamento ) {
        if ( horarioAbertura == null || horarioAbertura.isEmpty( ) ) {
            throw new DadoVazioException( "Horário de abertura do restaurante não pode ser vazio." );
        }

        if ( horarioFechamento == null || horarioFechamento.isEmpty( ) ) {
            throw new DadoVazioException( "Horário de fechamento do restaurante não pode ser vazio." );
        }

        Pattern horarioPattern = Pattern.compile( "^([01]?[0-9]|2[0-3]):[0-5][0-9]$" );

        if ( !horarioPattern.matcher(horarioAbertura ).matches( ) ) {
            throw new DadoInvalidoException("Horário de abertura deve estar no formato HH:mm (exemplo: 08:30).");
        }

        if ( !horarioPattern.matcher(horarioFechamento ).matches( ) ) {
            throw new DadoInvalidoException( "Horário de fechamento deve estar no formato HH:mm (exemplo: 22:30)." );
        }
    }

    private void validaNome( String nome )
    {
        if( nome.isEmpty( ) ){
            throw new DadoVazioException( "Nome do restaurante não pode ser vazio." );
        }
    }

    private void validaEndereco( String endereco )
    {
        if( endereco.isEmpty( ) ){
            throw new DadoVazioException( "Endereço do restaurante não pode ser vazio." );
        }
    }

    private void validaTipoCozinha( String tipoCozinha )
    {
        if( tipoCozinha.isEmpty( ) ){
            throw new DadoVazioException( "Tipo da cozinha do restaurante não pode ser vazio." );
        }
    }

    private LocalTime converterStringParaLocalTime( String horario ) {
        String[] partes = horario.split(":");
        int hora = Integer.parseInt(partes[0]);
        int minuto = Integer.parseInt(partes[1]);
        return LocalTime.of(hora, minuto);
    }

    public void setNome(String nome) {
        validaNome(nome);
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        validaEndereco(endereco);
        this.endereco = endereco;
    }

    public void setTipoCozinha(String tipoCozinha) {
        validaTipoCozinha(tipoCozinha);
        this.tipoCozinha = tipoCozinha;
    }

    public void setHorarioFuncionamento(String horarioAbertura, String horarioFechamento) {
        validaHorarioFuncionamento(horarioAbertura, horarioFechamento);
        this.horarioFuncionamento = new HorarioFuncionamento(
            converterStringParaLocalTime(horarioAbertura),
            converterStringParaLocalTime(horarioFechamento)
        );
    }
}

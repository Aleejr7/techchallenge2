package br.com.fiap.techchallenge2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Techchallenge2Application
{

	public static void main(String[] args) {

		// Criar uma senha a partir de uma senha em texto simples com PasswordEncoder
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		System.out.println("Senha admin123 codificada: " + passwordEncoder.encode("admin123"));
//		System.out.println("Senha password codificada: " + passwordEncoder.encode("password"));

		SpringApplication.run( Techchallenge2Application.class, args);
	}

}

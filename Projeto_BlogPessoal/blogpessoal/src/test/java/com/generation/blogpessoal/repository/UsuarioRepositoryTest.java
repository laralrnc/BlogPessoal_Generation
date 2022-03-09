package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

//indica que é uma classe de teste
//RANDOM_PORT = busca uma porta para rodar, quando a padrao estiver ocupada
//PER_CLASS = roda os testes por classe
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	public UsuarioRepository usuarioRepository;

	//PRIMEIRO METODO A SER EXECUTADO OBVIOOOOOOOOOOOO
	@BeforeAll
	void start() {
		//PARA ZERAR O BANCO ANTES DE COMEÇAR OS TESTES
		usuarioRepository.deleteAll();
		
		// 0L = Long e 0 quando não sabe qual o ID
		usuarioRepository.save(
				new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));

		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278",
				"https://i.imgur.com/NtyGneo.jpg"));

		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278",
				"https://i.imgur.com/mB3VM2N.jpg"));

		usuarioRepository.save(
				new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
	}
	
	//@TEST = MARCA O METODO COMO TESTE
	//DISPLAY NAME = NOME DE IDENTIFICAÇÃO
	//TODOS SÃO VOID PQ METODO DE TESTE NÃO RETORNA
	//DO TIPO OPTIONAL PQ PODE RETORNAR VAZIO
	//CADA TESTE DEVE FAZER UMA VERIFICAÇÃO
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}

    @AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}

}

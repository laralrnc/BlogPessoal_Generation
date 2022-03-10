package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) = ordena os testes
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	//funciona como o insomnia. cria a requisição
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

    @Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){

		usuarioRepository.deleteAll();
	}
	//@Order(1) = signifca que será o primeiro teste a ser executado
	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {
		
		//criando um objeto da classe HTTP que carregará um objeto da classe Usuario, chamado "requisicao"
		//CADASTRANDO UM USUARIO PARA O TESTE
		HttpEntity<Usuario> CORPOrequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Paulo Antunes", "paulo_antunes@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
		
		//responseentity receberá o resultado da requisição. usa o testRestTemplate para enviar a requisição, com o caminho
		//"/usuarios/cadastrar" - O que essa requisição fará? HttpMethod.POST = cadastrar usuario no banco H2, 
		//Qual usuário? o objeto que está no campo da requisição = CORPOrequisicao, Usuario.class
		ResponseEntity<Usuario> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, CORPOrequisicao, Usuario.class);
		
		//se o cadastro estiver correto o STATUSHTTP será = CREATED
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		//VERIFICA SE OS DADOS ENVIADOS ESTÃO IGUAIS AOS DADOS RECEBIDOS
		//CORPOrequisicao.getBody().getNome(), DADOS QUE ENVIEI
		//resposta.getBody().getNome() DADOS QUE RECEBI
		assertEquals(CORPOrequisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(CORPOrequisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		//cadastro pela service. cadastrarUsuario
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
		
		//cadastro pelo corpo da requisição
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
		
		//responseentity receberá o resultado da requisição. usa o testRestTemplate para enviar a requisição, com o caminho
		//"/usuarios/cadastrar" - O que essa requisição fará? HttpMethod.POST = cadastrar usuario no banco H2, 
		//Qual usuário? o objeto que está no campo da requisição = CORPOrequisicao, Usuario.class
		ResponseEntity<Usuario> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUmUsuario() {

		//usa optional
		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Juliana Andrews", "juliana_andrews@email.com.br", 
			"juliana123", "https://i.imgur.com/yDRVeK7.jpg"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), 
			"Juliana Andrews Ramos", "juliana_ramos@email.com.br", 
			"juliana123", "https://i.imgur.com/yDRVeK7.jpg");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Sabrina Sanches", "sabrina_sanches@email.com.br", 
			"sabrina123", "https://i.imgur.com/5M2p5Wb.jpg"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Ricardo Marques", "ricardo_marques@email.com.br", 
			"ricardo123", "https://i.imgur.com/Sk5SjWE.jpg"));

		ResponseEntity<String> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@Order(5)
	@DisplayName("Listar Um Usuário Específico")
	public void deveListarApenasUmUsuario() {
		
		/**
		 * Persiste um objeto da Classe Usuario no Banco de dados através do Objeto da Classe UsuarioService e
		 * guarda o objeto persistido no Banco de Dadoas no Objeto usuarioCadastrado, que será reutilizado abaixo. 
		 * 
		 * O Objeto usuarioCadastrado será do tipo Optional porquê caso o usuário não seja persistido no Banco 
		 * de dados, o Optional evitará o erro NullPointerException (Objeto Nulo).
		 */
		Optional<Usuario> usuarioBusca = usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Laura Santolia", "laura_santolia@email.com.br", "laura12345", "https://i.imgur.com/EcJG8kB.jpg"));
			
		/**
		 * Cria um Objeto da Classe ResponseEntity (corpoResposta), que receberá a Resposta da Requisição que será 
		 * enviada pelo Objeto da Classe TestRestTemplate.
		 * 
		 * Na requisição HTTP será enviada a URL do recurso ("/usuarios/" + usuarioBusca.get().getId()), o verbo (GET), a entidade
		 * HTTP será nula (Requisição GET não envia nada no Corpo da Requisição) e a Classe de retorno da Resposta 
		 * (String), porquê a lista de Usuários será do tipo String.
		 * 
		 * Para obtero Id de forma automática, foi utilizado o método getId() do Objeto usuarioBusca.
		 * 
		 * Observe que o Método All não está liberado de autenticação (Login do usuário), por isso utilizamos o
		 * Método withBasicAuth para autenticar o usuário em memória, criado na BasicSecurityConfig.
		 * 
		 * Usuário: root
		 * Senha: root
		 */
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/" + usuarioBusca.get().getId(), HttpMethod.GET, null, String.class);
		
		/**
		 *  Verifica se a requisição retornou o Status Code OK (200) 
		 * Se for verdadeira, o teste passa, se não, o teste falha.
		 */
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}

	@Test
	@Order(6)
	@DisplayName("Login do Usuário")
	public void deveAutenticarUsuario() {

		/**
		 * Persiste um objeto da Classe Usuario no Banco de dados através do Método cadastrarUsuario
		 * da Classe UsuarioService
		 */
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Marisa Souza", "marisa_souza@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));

		/**
		 * Cria um Objeto da Classe UsuarioLogin dentro de um Objeto da Classe HttpEntity (Entidade HTTP).
		 * O Objeto desta Classe será preenchido apenas como o usuário e senha do usuário criado acima.
		 */
		HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<UsuarioLogin>(new UsuarioLogin(0L, 
			"", "marisa_souza@email.com.br", "13465278", "", ""));

		/**
		 * Cria um Objeto da Classe ResponseEntity (corpoResposta), que receberá a Resposta da Requisição que será 
		 * enviada pelo Objeto da Classe TestRestTemplate.
		 * 
		 * Na requisição HTTP será enviada a URL do recurso (/usuarios/logar), o verbo (POST), a entidade
		 * HTTP criada acima (corpoRequisicao) e a Classe de retornos da Resposta (UsuarioLogin).
		 */
		ResponseEntity<UsuarioLogin> corpoResposta = testRestTemplate
			.exchange("/usuarios/logar", HttpMethod.POST, corpoRequisicao, UsuarioLogin.class);

		/**
		 *  Verifica se a requisição retornou o Status Code OK (200) 
		 * Se for verdadeira, o teste passa, se não, o teste falha.
		 */
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

	}

	

}

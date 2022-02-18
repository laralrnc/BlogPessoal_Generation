package com.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
//configuração de link front e back, 
//caso não tenha essa configuração o front e back só irá funcionar se estiverem configurados na mesma máquina
//possibilita que cada parte funcione em um servidor diferente
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	//criação de objeto para liberar acesso aos metodos de manipulação dos dados objeto = postagemRepository
	@Autowired
	private PostagemRepository postagemRepository;
	
	// criação do metodo de exibição dos dados da tabelas
	@GetMapping 
	public ResponseEntity <List <Postagem>> getAll()
	{
		//findAll = exibe todos os dados =select *from tb_postagens
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	//findByID
	//"/{id}" variavel de caminho
	//PathVariable anotação de caminho
	// http://localhost:8080/postagens/3. 3 = "/{id}"
	@GetMapping("/{id}")
	public ResponseEntity <Postagem> getById(@PathVariable Long id)
	{
		//lambda = (resposta -> retorno)
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//("/titulo/{titulo}") == ("/nomeColuna/{Valor da variavel}")
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity <List <Postagem>> getByTitulo(@PathVariable String titulo)
	{
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	//pode usar o mesmo caminho na url, contando que o verbo seja diferente
	//inserindo um objeto no banco (todas as colunas)
	@PostMapping
	public ResponseEntity<Postagem> postPostagem (@Valid @RequestBody Postagem postagem)
	{
		return ResponseEntity.status(HttpStatus.CREATED).
				body(postagemRepository.save(postagem));
	}
	//metodo de atualização
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@Valid @RequestBody Postagem postagem)
	{
		return ResponseEntity.status(HttpStatus.OK).
				body(postagemRepository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable Long id)
	{
		postagemRepository.deleteById(id);
	}
}

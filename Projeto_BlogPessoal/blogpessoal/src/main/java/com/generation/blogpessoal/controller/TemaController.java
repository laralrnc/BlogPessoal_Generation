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
import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
// @RequestMapping("/temas") = endereço da url
//configuração de link front e back, 
//caso não tenha essa configuração o front e back só irá funcionar se estiverem configurados na mesma máquina
//possibilita que cada parte funcione em um servidor diferente
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	//criação de objeto para liberar acesso aos metodos de manipulação dos dados objeto = temaRepository
	@Autowired
	private TemaRepository temaRepository;
	
	// criação do metodo de exibição dos dados da tabelas
	@GetMapping 
	public ResponseEntity <List <Tema>> getAll()
	{
		//findAll = exibe todos os dados =select *from tb_temas
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	//pode usar o mesmo caminho na url, contando que o verbo seja diferente
	//inserindo um objeto no banco (todas as colunas)
	@PostMapping
	public  ResponseEntity <Tema> postTema(@Valid @RequestBody Tema tema)
	{
		return ResponseEntity.status(HttpStatus.CREATED).
				body(temaRepository.save(tema));
	}
	
	//findByID
	//"/{id}" variavel de caminho
	//PathVariable anotação de caminho
	// http://localhost:8080/postagens/3. 3 = "/{id}"
	@GetMapping("/{id}")
	public ResponseEntity <Tema> getById(@PathVariable Long id)
	{
		//lambda = (resposta -> retorno)
		return temaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//("/titulo/{titulo}") == ("/nomeColuna/{Valor da variavel}")
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity <List <Tema>> getByTitulo(@PathVariable String descricao)
	{
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	//metodo de atualização
	//
	@PutMapping
	public ResponseEntity<Tema> putTema (@Valid @RequestBody Tema tema)
	{
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
				.body(temaRepository.save(tema)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTema(@PathVariable Long id)
	{
		return temaRepository.findById(id).map(resposta -> 
		{
			temaRepository.deleteById(id);
				return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}
 }
